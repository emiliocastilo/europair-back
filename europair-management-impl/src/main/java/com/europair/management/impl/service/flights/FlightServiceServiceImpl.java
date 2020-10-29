package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightServiceDto;
import com.europair.management.impl.mappers.flights.IFlightServiceMapper;
import com.europair.management.impl.service.calculation.ICalculationService;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.entity.FlightService;
import com.europair.management.rest.model.flights.repository.FlightRepository;
import com.europair.management.rest.model.flights.repository.FlightServiceRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import com.europair.management.rest.model.services.entity.Service;
import com.europair.management.rest.model.services.repository.ServiceRepository;
import com.europair.management.rest.model.users.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

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
    private IUserRepository userRepository;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FlightService not found with id: " + id)));
    }

    @Override
    public List<FlightServiceDto> saveFlightService(Long fileId, Long routeId, FlightServiceDto flightServiceDto) {
        validatePathIds(fileId);
        Route route = getRoute(routeId);
        if (flightServiceDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New FlightService expected. Identifier %s got", flightServiceDto.getId()));
        }
        if (CollectionUtils.isEmpty(flightServiceDto.getFlightIdList())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No flight ids found in request body");
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
                    calculateVat(fileId, serviceType, flight, flightServiceDto);
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FlightService not found with id: " + id));

        // Recalculate vat
        calculateVat(fileId, serviceType, flight, flightServiceDto);

        IFlightServiceMapper.INSTANCE.updateFromDto(flightServiceDto, flightService);
        flightService = flightServiceRepository.save(flightService);
        // ToDo: Log result ok??
    }

    @Override
    public void deleteFlightService(Long fileId, Long routeId, Long flightId, Long id) {
        validatePathIds(fileId, routeId, flightId);
        if (!flightServiceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FlightService not found with id: " + id);
        }
        flightServiceRepository.deleteById(id);
    }


    private void validatePathIds(final Long fileId, final Long routeId, final Long flightId) {
        validatePathIds(fileId);
        if (!routeRepository.existsById(routeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId);
        }
        if (!flightRepository.existsById(flightId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + flightId);
        }
    }

    private void validatePathIds(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
    }

    private Route getRoute(@NotNull Long routeId) {
        return routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No route found with id: " + routeId));
    }

    private Flight getFlight(@NotNull Long flightId) {
        return flightRepository.findById(flightId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No flight found with id: " + flightId));
    }

    private Service getService(@NotNull Long serviceTypeId) {
        return serviceTypeRepository.findById(serviceTypeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No service type found with id: " + serviceTypeId));
    }

    private void calculateVat(final Long fileId, final Service serviceType, final Flight flight, final FlightServiceDto flightServiceDto) {
        final Airport origin = flight.getOrigin();
        final Airport destination = flight.getDestination();

        Double taxOnSale = calculationService.calculateFinalTaxToApply(fileId, origin, destination, serviceType.getType(), true);
        Double taxOnPurchase = calculationService.calculateFinalTaxToApply(fileId, origin, destination, serviceType.getType(), false);

        // Update dto values
        flightServiceDto.setTaxOnSale(taxOnSale);
        flightServiceDto.setTaxOnPurchase(taxOnPurchase);
    }

}
