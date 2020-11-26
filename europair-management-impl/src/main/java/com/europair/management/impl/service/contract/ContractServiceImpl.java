package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.api.enums.ContractStatesEnum;
import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.enums.PurchaseSaleEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.api.enums.ServiceTypeEnum;
import com.europair.management.impl.common.service.IStateChangeService;
import com.europair.management.impl.mappers.contract.IContractLineMapper;
import com.europair.management.impl.mappers.contract.IContractMapper;
import com.europair.management.impl.service.flights.IFlightServiceService;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.contracts.entity.Contract;
import com.europair.management.rest.model.contracts.entity.ContractLine;
import com.europair.management.rest.model.contracts.repository.ContractLineRepository;
import com.europair.management.rest.model.contracts.repository.ContractRepository;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContractServiceImpl implements IContractService {

    private final String FILE_ID_FILTER = "file.id";

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractLineRepository contractLineRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private IStateChangeService stateChangeService;

    @Autowired
    private IFlightServiceService flightServiceService;

    @Override
    public Page<ContractDto> findAllPaginatedByFilter(final Long fileId, Pageable pageable, CoreCriteria criteria) {
        checkIfFileExists(fileId);
        Utils.addCriteriaIfNotExists(criteria, FILE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(fileId));

        return contractRepository.findContractByCriteria(criteria, pageable)
                .map(IContractMapper.INSTANCE::toDtoNoLines);
    }

    @Override
    public ContractDto findById(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        return IContractMapper.INSTANCE.toDto(contractRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + id)));
    }

    @Override
    public ContractDto saveContract(final Long fileId, ContractDto contractDto) {
        checkIfFileExists(fileId);
        contractDto.setFileId(fileId);
        if (contractDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Contract expected. Identifier %s got", contractDto.getId()));
        }

        Contract contract = IContractMapper.INSTANCE.toEntity(contractDto);
        contract = contractRepository.save(contract);

        return IContractMapper.INSTANCE.toDto(contract);
    }

    @Override
    public ContractDto updateContract(final Long fileId, Long id, ContractDto contractDto) {
        checkIfFileExists(fileId);
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + id));
        IContractMapper.INSTANCE.updateFromDto(contractDto, contract);
        contract = contractRepository.save(contract);

        return IContractMapper.INSTANCE.toDto(contract);
    }

    @Override
    public void deleteContract(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + id));
        contract.setRemovedAt(LocalDateTime.now());
        contract = contractRepository.save(contract);
    }

    @Override
    public void generateContracts(@NotNull Long fileId, @NotEmpty List<Long> routeIds) {
        File file = fileRepository.findById(fileId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId));
        List<Route> routes = routeRepository.findAllByIdIn(routeIds);

        // Validate routes
        if (!routes.stream().allMatch(route -> fileId.equals(route.getFileId()) && RouteStatesEnum.WON.equals(route.getRouteState()))) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Cannot generate contracts. All the routes have to be in state WON.");
        }

        Map<Long, List<Contribution>> operatorIdContributionsMap = routes.stream()
                .flatMap(route -> route.getContributions().stream())
                .filter(contribution -> ContributionStatesEnum.WON.equals(contribution.getContributionState()))
                .collect(Collectors.groupingBy(Contribution::getOperatorId, Collectors.mapping(o -> o, Collectors.toList())));

        final LocalDateTime contractDate = LocalDateTime.now();

        List<ContractLine> purchaseContractLines = new ArrayList<>();
        List<ContractLine> saleContractLines = new ArrayList<>();
        for (List<Contribution> list : operatorIdContributionsMap.values()) {
            // Purchase contract
            Contract purchaseContract = new Contract();
            purchaseContract.setFileId(fileId);
            purchaseContract.setContractType(PurchaseSaleEnum.PURCHASE);
            purchaseContract.setCode(generateContractCode());
            purchaseContract.setContractState(ContractStatesEnum.PENDING);
            purchaseContract.setProviderId(file.getProviderId()); // ToDo: operador de las contribuciones?
            purchaseContract.setContractDate(contractDate);

            // Save contract to persist id and code
            purchaseContract = contractRepository.save(purchaseContract);

            // Create and persist purchase contract lines
            purchaseContractLines.addAll(generatePurchaseContractLines(purchaseContract.getId(), list));

            // Create sale contract lines
            saleContractLines.addAll(generateSaleContractLines(list));
        }

        // Sale contract
        Contract saleContract = new Contract();
        saleContract.setFileId(fileId);
        saleContract.setContractType(PurchaseSaleEnum.SALE);
        saleContract.setCode(generateContractCode());
        saleContract.setContractState(ContractStatesEnum.PENDING);
        saleContract.setClientId(file.getClientId());
        saleContract.setContractDate(contractDate);

        saleContract = contractRepository.save(saleContract);
        final Long saleContractId = saleContract.getId();
        saleContractLines = saleContractLines.stream()
                .map(line -> {
                    line.setContractId(saleContractId);
                    return line;
                }).collect(Collectors.toList());

        // Persist all contract lines
        saleContractLines.addAll(purchaseContractLines);
        List<ContractLine> contractLinesSaved = contractLineRepository.saveAll(saleContractLines);

        // ToDo: generar servicios adicionales

        // Change file state
        stateChangeService.changeState(Collections.singletonList(fileId), FileStatesEnum.BLUE_BOOKED);
    }

    private void checkIfFileExists(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
    }

    private String generateContractCode() {
        final int CODE_INIT_VALUE = 0;

        StringBuilder sb = new StringBuilder();

        // Code to filter
        sb.append((LocalDate.now().getYear()));

        // Find last code if exists
        final String codeFilterValue = sb.toString();
        final Integer lastCodeOfCurrentYear = contractRepository.findByCodeStartsWith(codeFilterValue).stream()
                .map(contract -> Integer.valueOf(contract.getCode().replace(codeFilterValue, "")))
                .max(Integer::compareTo)
                .orElse(null);

        // Code for new Contract
        sb.append(StringUtils.leftPad(String.valueOf(lastCodeOfCurrentYear == null ? CODE_INIT_VALUE : lastCodeOfCurrentYear + 1),
                6, "0"));

        return sb.toString();
    }

    private List<ContractLine> generatePurchaseContractLines(final Long contractId, final List<Contribution> contributions) {
        List<ContractLine> contractLines = null;
        if (!CollectionUtils.isEmpty(contributions)) {
            contractLines = contributions.stream()
                    .flatMap(contribution -> contribution.getLineContributionRoute().stream())
                    .filter(line -> ServiceTypeEnum.FLIGHT.equals(line.getType()) && line.getFlightId() == null
                            && PurchaseSaleEnum.PURCHASE.equals(line.getLineContributionRouteType()))
                    .map(line -> {
                        ContractLine contractLine = IContractLineMapper.INSTANCE.toContractLineFromContributionLine(line);
                        contractLine.setContractId(contractId);
                        contractLine.setCurrency(line.getContribution().getCurrency());
                        contractLine.setVatPercentage(line.getContribution().getPurchaseCommissionPercent());
                        contractLine.setVatAmount(
                                (contractLine.getPrice() == null || contractLine.getVatPercentage() == null) ? null :
                                        contractLine.getPrice().multiply(BigDecimal.valueOf(contractLine.getVatPercentage() / 100)));
                        return contractLine;
                    }).collect(Collectors.toList());
        }

        return contractLines;
    }

    private List<ContractLine> generateSaleContractLines(final List<Contribution> contributions) {
        List<ContractLine> contractLines = null;
        if (!CollectionUtils.isEmpty(contributions)) {
            contractLines = contributions.stream()
                    .flatMap(contribution -> contribution.getLineContributionRoute().stream())
                    .filter(line -> ServiceTypeEnum.FLIGHT.equals(line.getType()) && line.getFlightId() == null
                            && PurchaseSaleEnum.SALE.equals(line.getLineContributionRouteType()))
                    .map(line -> {
                        ContractLine contractLine = IContractLineMapper.INSTANCE.toContractLineFromContributionLine(line);
                        contractLine.setCurrency(line.getContribution().getCurrencyOnSale());
                        contractLine.setVatPercentage(line.getContribution().getSalesCommissionPercent());
                        contractLine.setVatAmount(
                                (contractLine.getPrice() == null || contractLine.getVatPercentage() == null) ? null :
                                        contractLine.getPrice().multiply(BigDecimal.valueOf(contractLine.getVatPercentage() / 100)));
                        return contractLine;
                    }).collect(Collectors.toList());
        }

        return contractLines;
    }

    private void generateFlightServices(Contribution contribution) {
        // Key = service type, Left = purchase line, Right = sale line
        Map<ServiceTypeEnum, Pair<LineContributionRoute, LineContributionRoute>> additionalServicesMap = new HashMap<>();
        contribution.getLineContributionRoute().stream()
                .filter(line -> !ServiceTypeEnum.FLIGHT.equals(line.getType()))
                .forEach(line -> {
                    if (additionalServicesMap.containsKey(line.getType())) {
                        if (PurchaseSaleEnum.PURCHASE.equals(line.getLineContributionRouteType())) {
                            additionalServicesMap.put(line.getType(),
                                    Pair.of(line, additionalServicesMap.get(line.getType()).getRight()));
                        } else {
                            additionalServicesMap.put(line.getType(),
                                    Pair.of(additionalServicesMap.get(line.getType()).getLeft(), line));
                        }
                    } else {
                        additionalServicesMap.put(line.getType(), Pair.of(
                                PurchaseSaleEnum.PURCHASE.equals(line.getLineContributionRouteType()) ? line : null,
                                PurchaseSaleEnum.SALE.equals(line.getLineContributionRouteType()) ? line : null
                        ));
                    }
                });

        // ToDo - WIP
        /*
        additionalServicesMap.values().stream()
                .map(pair -> {
                    LineContributionRoute purchaseLine = pair.getLeft();
                    LineContributionRoute saleLine = pair.getRight();
                    Route selectedRoute = purchaseLine != null ? purchaseLine.getRoute() : saleLine.getRoute();
                    List<Long> flightIds = selectedRoute.getParentRouteId() == null ?
                            selectedRoute.getRotations().stream()
                                    .map(Route::getFlights).flatMap(Collection::stream).map(Flight::getId).collect(Collectors.toList()) :
                            selectedRoute.getFlights().stream().map(Flight::getId).collect(Collectors.toList());

                });

        List<FlightService> flightServiceList = contribution.getLineContributionRoute().stream()
                .filter(line -> !ServiceTypeEnum.FLIGHT.equals(line.getType()))

                .map(line -> {
                    FlightService fs = new FlightService();
                    fs.setFlightId(line.getFlightId());
                    fs.setComments(line.getComments());
                    fs.setCommission(line.get);
//                    fs.setDescription();
                    fs.setPercentageAppliedOnPurchaseTax();
                    fs.setPercentageAppliedOnSaleTax();
//                    fs.setProviderId();
                    fs.setPurchasePrice(line.getg);
                    fs.setQuantity();
                    fs.setSalePrice();
                    fs.setSellerId();
                    fs.setServiceId();
                    fs.setTaxOnPurchase();
                    fs.setTaxOnSale(line.get);
                });

         */
    }

}
