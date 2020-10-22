package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.contribution.LineContributionRouteDTO;
import com.europair.management.api.enums.LineContributionRouteType;
import com.europair.management.api.enums.ServiceTypeEnum;
import com.europair.management.impl.mappers.contributions.IContributionMapper;
import com.europair.management.impl.mappers.contributions.ILineContributionRouteMapper;
import com.europair.management.impl.service.flights.IFlightTaxService;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.contributions.repository.LineContributionRouteRepository;
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

@Service
@Transactional(readOnly = true)
public class ContributionServiceImpl implements IContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private IFlightTaxService flightTaxService;

    @Autowired
    private LineContributionRouteRepository lineContributionRouteRepository;

    @Override
    public Page<ContributionDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contributionRepository.findContributionByCriteria(criteria, pageable).map(IContributionMapper.INSTANCE::toDto);
    }

    @Override
    public Page<LineContributionRouteDTO> findAllPaginatedLineContributionRouteByFilter(Pageable pageable, CoreCriteria criteria) {
        return lineContributionRouteRepository.findLineContributionRouteByCriteria(criteria, pageable).map(ILineContributionRouteMapper.INSTANCE::toDto);
    }

    @Override
    public ContributionDTO findById(Long id) {
        return IContributionMapper.INSTANCE.toDto(contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = false)
    public ContributionDTO saveContribution(ContributionDTO contributionDTO) {
        if (contributionDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Contribution expected. Identifier %s got", contributionDTO.getId()));
        }

        Contribution contribution = IContributionMapper.INSTANCE.toEntity(contributionDTO);
        contribution = contributionRepository.save(contribution);

        // must activate flag in route to indicate the route has a contribution
        final Long routeId = contribution.getRouteId();
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));
        route.setHasContributions(true);
        routeRepository.save(route);

        // Add flight taxes for the contribution
        List<FlightTax> flightTaxes = flightTaxService.saveFlightTaxes(contribution, route);

        // Add route contribution lines
        Set<LineContributionRoute> routeContributionLines = createRouteContributionLines(contribution.getId(), route);

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
    @Transactional(readOnly = false)
    public ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id));
        IContributionMapper.INSTANCE.updateFromDto(contributionDTO, contribution);
        contribution = contributionRepository.save(contribution);

        return IContributionMapper.INSTANCE.toDto(contribution);
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean updateLineContributionRoute(Long contributionId, Long lineContributionRouteId, LineContributionRouteDTO lineContributionRouteDTO) {
        Boolean result = false;
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
    @Transactional(readOnly = false)
    public void deleteContribution(Long id) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id));

        contribution.setRemovedAt(LocalDateTime.now());
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

}
