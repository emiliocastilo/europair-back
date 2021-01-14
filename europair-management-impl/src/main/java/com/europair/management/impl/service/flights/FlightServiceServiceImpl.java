package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightServiceDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.flights.IFlightServiceMapper;
import com.europair.management.impl.service.calculation.ICalculationService;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.common.exception.EuropairForeignTaxException;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.entity.FlightService;
import com.europair.management.rest.model.flights.repository.FlightRepository;
import com.europair.management.rest.model.flights.repository.FlightServiceRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import com.europair.management.rest.model.services.entity.Service;
import com.europair.management.rest.model.services.repository.ServiceRepository;
import com.europair.management.rest.model.users.repository.UserRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class FlightServiceServiceImpl implements IFlightServiceService {

    private final String FLIGHT_ID_FILTER = "flight.id";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlightServiceRepository flightServiceRepository;

    @Autowired
    private ServiceRepository serviceTypeRepository;

    @Autowired
    private ICalculationService calculationService;


    @Override
    public Page<FlightServiceDto> findAllPaginatedByFilter(Long fileId, Long routeId, Long flightId, Pageable pageable, CoreCriteria criteria) {
        validatePathIds(fileId, routeId, flightId);
        Utils.addCriteriaIfNotExists(criteria, FLIGHT_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(flightId));

        return flightServiceRepository.findFlightServiceByCriteria(criteria, pageable)
                .map(IFlightServiceMapper.INSTANCE::toDto);
    }

    @Override
    public FlightServiceDto findById(Long fileId, Long routeId, Long flightId, Long id) {
        validatePathIds(fileId, routeId, flightId);
        return IFlightServiceMapper.INSTANCE.toDto(flightServiceRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FLIGHT_SERVICE_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public List<FlightServiceDto> saveFlightService(Long fileId, Long routeId, FlightServiceDto flightServiceDto) {
        validatePathIds(fileId);
        Route route = getRoute(routeId);
        if (flightServiceDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FLIGHT_SERVICE_NEW_WITH_ID, String.valueOf(flightServiceDto.getId()));
        }
        if (CollectionUtils.isEmpty(flightServiceDto.getFlightIdList())) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FLIGHT_SERVICE_NO_FLIGHT_IDS);
        }

        Service serviceType = getService(flightServiceDto.getServiceId());

        // Set seller id
        if (flightServiceDto.getSellerId() == null) {
            flightServiceDto.setSellerId(Utils.GetUserFromSecurityContext.getLoggedUserId(this.userRepository));
        }

        List<FlightService> flightServices = flightServiceDto.getFlightIdList().stream()
                .map(flightId -> {
                    Flight flight = getFlight(flightId);
                    FlightService flightService = IFlightServiceMapper.INSTANCE.toEntity(flightServiceDto);
                    flightService.setFlightId(flightId);
                    // Calculate VAT
                    calculateVat(fileId, serviceType, flight, flightService);
                    return flightService;
                }).collect(Collectors.toList());

        flightServices = flightServiceRepository.saveAll(flightServices);

        return flightServices.stream().map(IFlightServiceMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateFlightService(Long fileId, Long routeId, Long flightId, Long id, FlightServiceDto flightServiceDto) {
        validatePathIds(fileId);
        getRoute(routeId);
        Flight flight = getFlight(flightId);
        Service serviceType = getService(flightServiceDto.getServiceId());
        FlightService flightService = flightServiceRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FLIGHT_SERVICE_NOT_FOUND, String.valueOf(id)));

        IFlightServiceMapper.INSTANCE.updateFromDto(flightServiceDto, flightService);
        // Recalculate vat
        calculateVat(fileId, serviceType, flight, flightService);

        flightService = flightServiceRepository.save(flightService);
        // ToDo: Log result ok??
    }

    @Override
    public void deleteFlightService(Long fileId, Long routeId, Long flightId, Long id) {
        validatePathIds(fileId, routeId, flightId);
        if (!flightServiceRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FLIGHT_SERVICE_NOT_FOUND, String.valueOf(id));
        }
        flightServiceRepository.deleteById(id);
    }


    private void validatePathIds(final Long fileId, final Long routeId, final Long flightId) {
        validatePathIds(fileId);
        if (!routeRepository.existsById(routeId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NOT_FOUND, String.valueOf(routeId));
        }
        if (!flightRepository.existsById(flightId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FLIGHT_NOT_FOUND, String.valueOf(flightId));
        }
    }

    private void validatePathIds(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NOT_FOUND, String.valueOf(fileId));
        }
    }

    private Route getRoute(@NotNull Long routeId) {
        return routeRepository.findById(routeId).orElseThrow(() ->
                Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NOT_FOUND, String.valueOf(routeId)));
    }

    private Flight getFlight(@NotNull Long flightId) {
        return flightRepository.findById(flightId).orElseThrow(() ->
                Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FLIGHT_NOT_FOUND, String.valueOf(flightId)));
    }

    private Service getService(@NotNull Long serviceTypeId) {
        return serviceTypeRepository.findById(serviceTypeId).orElseThrow(() ->
                Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FLIGHT_SERVICE_NEW_WITH_ID, String.valueOf(serviceTypeId)));
    }

    private void calculateVat(final Long fileId, final Service serviceType, final Flight flight, final FlightService flightService) {
        final Airport origin = flight.getOrigin();
        final Airport destination = flight.getDestination();

        // Update dto values
        try {
            Pair<Double, Double> saleTaxData = calculationService.calculateTaxToApplyAndPercentage(
                    fileId, origin, destination, serviceType.getType(), true);
            flightService.setTaxOnSale(saleTaxData.getLeft());
            flightService.setPercentageAppliedOnSaleTax(saleTaxData.getRight());
        } catch (EuropairForeignTaxException e) {
            flightService.setTaxOnSale(null);
            flightService.setPercentageAppliedOnSaleTax(calculationService.calculatePercentageOfTaxToApply(
                    origin, destination, serviceType.getType(), true));
        }
        try {
            Pair<Double, Double> purchaseTaxData = calculationService.calculateTaxToApplyAndPercentage(
                    fileId, origin, destination, serviceType.getType(), false);
            flightService.setTaxOnPurchase(purchaseTaxData.getLeft());
            flightService.setPercentageAppliedOnPurchaseTax(purchaseTaxData.getRight());
        } catch (EuropairForeignTaxException e) {
            flightService.setTaxOnPurchase(null);
            flightService.setPercentageAppliedOnPurchaseTax(calculationService.calculatePercentageOfTaxToApply(
                    origin, destination, serviceType.getType(), false));
        }
    }

}
