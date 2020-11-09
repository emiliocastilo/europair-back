package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.contribution.LineContributionRouteDTO;
import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.LineContributionRouteType;
import com.europair.management.api.enums.ServiceTypeEnum;
import com.europair.management.impl.common.service.IStateChangeService;
import com.europair.management.impl.mappers.contributions.IContributionMapper;
import com.europair.management.impl.mappers.contributions.ILineContributionRouteMapper;
import com.europair.management.impl.service.flights.IFlightTaxService;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.contributions.repository.LineContributionRouteRepository;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.flights.entity.FlightTax;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ContributionServiceImpl implements IContributionService {

    private final String CONTRIBUTION_ID_FILTER = "contributionId";
    private final String ROUTE_ID_FILTER = "routeId";

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private IFlightTaxService flightTaxService;

    @Autowired
    private LineContributionRouteRepository lineContributionRouteRepository;

    @Autowired
    private IStateChangeService stateChangeService;

    @Override
    public Page<ContributionDTO> findAllPaginatedByFilter(Long routeId, Pageable pageable, CoreCriteria criteria) {
        Utils.addCriteriaIfNotExists(criteria, ROUTE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(routeId));
        return contributionRepository.findContributionByCriteria(criteria, pageable).map(IContributionMapper.INSTANCE::toDto);
    }

    @Override
    public Page<LineContributionRouteDTO> findAllPaginatedLineContributionRouteByFilter(Long contributionId, Pageable pageable, CoreCriteria criteria) {
        Utils.addCriteriaIfNotExists(criteria, CONTRIBUTION_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(contributionId));
        return lineContributionRouteRepository.findLineContributionRouteByCriteria(criteria, pageable).map(ILineContributionRouteMapper.INSTANCE::toDto);
    }

    @Override
    public ContributionDTO findById(Long id) {
        return IContributionMapper.INSTANCE.toDto(contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id)));
    }

    @Override
    public synchronized ContributionDTO saveContribution(ContributionDTO contributionDTO) {
        if (contributionDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Contribution expected. Identifier %s got", contributionDTO.getId()));
        }

        Contribution contribution = IContributionMapper.INSTANCE.toEntity(contributionDTO);
        contribution.setRequestTime(LocalDateTime.now());
        contribution = contributionRepository.saveAndFlush(contribution);

        // must activate flag in route to indicate the route has a contribution
        final Long routeId = contribution.getRouteId();
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));
        route.setHasContributions(true);
        Route updatedRoute = routeRepository.saveAndFlush(route);

        // Retrieve seatings info from first flight of first rotation
        contribution.setSeatingC(route.getRotations().get(0).getFlights().get(0).getSeatsC());
        contribution.setSeatingF(route.getRotations().get(0).getFlights().get(0).getSeatsF());
        contribution.setSeatingY(route.getRotations().get(0).getFlights().get(0).getSeatsY());

        contribution = contributionRepository.saveAndFlush(contribution);

        // Add flight taxes for the contribution
        List<FlightTax> flightTaxes = flightTaxService.saveFlightTaxes(contribution, route);

        // Add route contribution lines
        Set<LineContributionRoute> routeContributionLines = createRouteContributionLines(contribution.getId(), updatedRoute);

        // Contribution taxes
        List<Double> taxOnPurchase = flightTaxes.stream().map(FlightTax::getTaxOnPurchase).distinct().collect(Collectors.toList());
        if (taxOnPurchase.size() == 1) {
            Double tax = taxOnPurchase.get(0);
            if (Utils.Constants.TAX_ERROR_FOREIGN_TAX.equals(tax)) {
                contribution.setPurchaseVATMsg("El IVA a aplicar corresponde a un pais extranjero.");
                tax = null;
            }
            contribution.setPurchaseCommissionPercent(tax == null ? null : tax.intValue());
        } else {
            contribution.setPurchaseCommissionPercent(null);
            contribution.setPurchaseVATMsg("Los vuelos de la ruta tienen un IVA diferente.");
        }

        List<Double> taxOnSale = flightTaxes.stream().map(FlightTax::getTaxOnSale).distinct().collect(Collectors.toList());
        if (taxOnSale.size() == 1) {
            Double tax = taxOnSale.get(0);
            if (Utils.Constants.TAX_ERROR_FOREIGN_TAX.equals(tax)) {
                contribution.setSaleVATMsg("El IVA a aplicar corresponde a un pais extranjero.");
                tax = null;
            }
            contribution.setSalesCommissionPercent(tax == null ? null : tax.intValue());
        } else {
            contribution.setSalesCommissionPercent(null);
            contribution.setSaleVATMsg("Los vuelos de la ruta tienen un IVA diferente.");
        }

        // Set VAT amounts
        contribution.setVatAmountOnPurchase(
                (contribution.getPurchasePrice() == null || contribution.getPurchaseCommissionPercent() == null) ? null
                        : contribution.getPurchasePrice().multiply(
                        BigDecimal.valueOf(Double.valueOf(contribution.getPurchaseCommissionPercent()) / 100)));
        contribution.setVatAmountOnSale(
                (contribution.getSalesPrice() == null || contribution.getSalesCommissionPercent() == null) ? null
                        : contribution.getSalesPrice().multiply(
                        BigDecimal.valueOf(Double.valueOf(contribution.getSalesCommissionPercent()) / 100)));

        contribution = contributionRepository.saveAndFlush(contribution);

        return IContributionMapper.INSTANCE.toDto(contribution);
    }

    @Override
    @Transactional(readOnly = false)
    public Long saveLineContributionRoute(LineContributionRouteDTO lineContributionRouteDTO) {
        Long response = null;

        //validate data
        Optional<Contribution> contribution = this.contributionRepository.findById(lineContributionRouteDTO.getContributionId());
        if (contribution.isPresent()) {

            Long contributionRouteId = contribution.get().getRouteId();
            if (lineContributionRouteDTO.getRouteId().equals(contributionRouteId)) {

                LineContributionRoute lineContributionRoute = ILineContributionRouteMapper.INSTANCE.toEntity(lineContributionRouteDTO);
                response = this.lineContributionRouteRepository.save(lineContributionRoute).getId();

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Route not found with id: %s", lineContributionRouteDTO.getRouteId()));
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Contribution not found with id: %s", lineContributionRouteDTO.getContributionId()));
        }

        return response;
    }

    @Override
    public ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id));

        // Update VAT msg
        if (!contributionDTO.getVatAmountOnPurchase().equals(contribution.getVatAmountOnPurchase())) {
            contribution.setPurchaseVATMsg(null);
        }
        if (!contributionDTO.getVatAmountOnSale().equals(contribution.getVatAmountOnSale())) {
            contribution.setSaleVATMsg(null);
        }

        IContributionMapper.INSTANCE.updateFromDto(contributionDTO, contribution);
        contribution = contributionRepository.save(contribution);

        return IContributionMapper.INSTANCE.toDto(contribution);
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean updateLineContributionRoute(Long routeId, Long contributionId, Long lineContributionRouteId, LineContributionRouteDTO lineContributionRouteDTO) {
        Boolean result = false;
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));
        if (!routeId.equals(lineContributionRouteDTO.getRouteId()) &&
                route.getRotations().stream().noneMatch(rotation -> rotation.getId().equals(lineContributionRouteDTO.getRouteId()))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Contribution lines doesn't match with route with id: " + routeId);
        }
        LineContributionRoute lineContributionRoute = this.lineContributionRouteRepository.findById(lineContributionRouteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Line Contribution Rotation not found : %s", lineContributionRouteId)));

        if (lineContributionRoute.getContributionId().equals(contributionId)) {
            ILineContributionRouteMapper.INSTANCE.updateFromDto(lineContributionRouteDTO, lineContributionRoute);
            this.lineContributionRouteRepository.save(lineContributionRoute);
            result = true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The given contribution id %s do not match with the related contribution id in the Line Contribution Rotation provided : %s"
                            , contributionId
                            , lineContributionRouteId));
        }

        return result;
    }

    @Override
    public void deleteContribution(Long id) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id));

        //same delete time to keep trazability
        LocalDateTime localDateTimeOfDelete = LocalDateTime.now();

        // we must delete LineContributionRoutes asociated with the contribution
        contribution.getLineContributionRoute().forEach(lineContributionRoute -> {
            lineContributionRoute.setRemovedAt(localDateTimeOfDelete);
            this.lineContributionRouteRepository.save(lineContributionRoute);
        });

        contribution.setRemovedAt(localDateTimeOfDelete);
        contributionRepository.save(contribution);

    }

    @Override
    @Transactional(readOnly = false)
    public void deleteLineContributionRoute(Long contributionId, Long lineContributionRouteId) {
        LineContributionRoute lineContributionRoute = this.lineContributionRouteRepository.findById(lineContributionRouteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Line Contribution Rotation not found with id : %s", lineContributionRouteId)));

        if (lineContributionRoute.getContributionId().equals(contributionId)) {
            lineContributionRoute.setRemovedAt(LocalDateTime.now());
            this.lineContributionRouteRepository.save(lineContributionRoute);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The given contribution id %s do not match with the related contribution id in the Line Contribution Rotation provided : %s"
                            , contributionId
                            , lineContributionRouteId));
        }
    }

    @Override
    public void updateStates(Long fileId, Long routeId, List<Long> contributionIds, ContributionStatesEnum state) {
        checkIfFileExists(fileId);
        checkIfRouteExists(routeId);
        stateChangeService.changeState(contributionIds, state);
    }

    @Override
    public List<String> getValidContributionStatesToChange(Long fileId, Long routeId, Long id) {
        checkIfFileExists(fileId);
        checkIfRouteExists(routeId);
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id));
        return Stream.of(ContributionStatesEnum.values())
                .filter(state -> stateChangeService.canChangeState(contribution, state))
                .map(ContributionStatesEnum::name)
                .collect(Collectors.toList());
    }

    @Override
    public void generateRouteContributionSaleLines(Long contributionId) {
        Contribution contribution = contributionRepository.findById(contributionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + contributionId));
        Set<LineContributionRoute> flightPurchaseLines = new HashSet<>();
        Set<LineContributionRoute> flightSaleLines = new HashSet<>();
        Set<LineContributionRoute> servicePurchaseLines = new HashSet<>();
        Set<LineContributionRoute> serviceSaleLines = new HashSet<>();
        contribution.getLineContributionRoute().forEach(line -> {
            if (ServiceTypeEnum.FLIGHT.equals(line.getType())) {
                if (LineContributionRouteType.PURCHASE.equals(line.getLineContributionRouteType())) {
                    flightPurchaseLines.add(line);
                } else {
                    flightSaleLines.add(line);
                }
            } else {
                if (LineContributionRouteType.PURCHASE.equals(line.getLineContributionRouteType())) {
                    servicePurchaseLines.add(line);
                } else {
                    serviceSaleLines.add(line);
                }
            }
        });

        // Update flight lines
        Set<LineContributionRoute> updatedFlightSaleLines = flightPurchaseLines.stream()
                .map(purchaseLine -> {
                    LineContributionRoute saleLine = flightSaleLines.stream()
                            .filter(line -> purchaseLine.getRouteId().equals(line.getRouteId()))
                            .findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                                    "No route contribution sale line found for rotation with id: " + purchaseLine.getRouteId()));
                    // Update values
                    saleLine.setPrice(purchaseLine.getPrice());
                    return saleLine;
                }).collect(Collectors.toSet());
        lineContributionRouteRepository.saveAll(updatedFlightSaleLines);

        // Remove service lines
        lineContributionRouteRepository.deleteAll(serviceSaleLines);

        // Create updated service lines
        Set<LineContributionRoute> updatedServiceSaleLines = servicePurchaseLines.stream()
                .map(line -> {
                    LineContributionRoute saleLine = new LineContributionRoute(line);
                    saleLine.setLineContributionRouteType(LineContributionRouteType.SALE);
                    return saleLine;
                }).collect(Collectors.toSet());
        lineContributionRouteRepository.saveAll(updatedServiceSaleLines);

        // Update contribution data
        contribution.setSalesPrice(contribution.getPurchasePrice());
        contribution.setCurrencyOnSale(contribution.getCurrency());
        contribution = contributionRepository.save(contribution);
    }

    private Set<LineContributionRoute> createRouteContributionLines(final Long contributionId, final Route contributionRoute) {
        if (CollectionUtils.isEmpty(contributionRoute.getRotations())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "NO rotations found for route with id: " + contributionRoute.getId());
        }
        Set<LineContributionRoute> lines = contributionRoute.getRotations().stream().map(rotation -> {
            List<LineContributionRoute> res = new ArrayList<>();

            // Purchase
            LineContributionRoute purchaseLine = new LineContributionRoute();
            purchaseLine.setContributionId(contributionId);
            purchaseLine.setRouteId(rotation.getId());
            purchaseLine.setPrice(BigDecimal.ZERO);
            purchaseLine.setType(ServiceTypeEnum.FLIGHT);
            purchaseLine.setLineContributionRouteType(LineContributionRouteType.PURCHASE);
            res.add(purchaseLine);

            // Sale
            LineContributionRoute saleLine = new LineContributionRoute();
            saleLine.setContributionId(contributionId);
            saleLine.setRouteId(rotation.getId());
            saleLine.setPrice(BigDecimal.ZERO);
            saleLine.setType(ServiceTypeEnum.FLIGHT);
            saleLine.setLineContributionRouteType(LineContributionRouteType.SALE);
            res.add(saleLine);

            return res;
        }).flatMap(Collection::stream).collect(Collectors.toSet());

        lines = new HashSet<>(lineContributionRouteRepository.saveAll(lines));
        return lines;
    }

    private void checkIfRouteExists(Long routeId) {
        if (!routeRepository.existsByIdAndRemovedAtIsNull(routeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId);
        }
    }

    private void checkIfFileExists(Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
    }
}
