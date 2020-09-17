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
import com.europair.management.rest.model.flights.repository.FlightServiceRepository;
import com.europair.management.rest.model.flights.repository.IFlightRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.entity.RouteAirport;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightServiceServiceImpl implements IFlightServiceService {

    private final String FLIGHT_ID_FILTER = "flight.id";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private FlightServiceRepository flightServiceRepository;

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
    public FlightServiceDto saveFlightService(Long fileId, Long routeId, Long flightId, FlightServiceDto flightServiceDto) {
        validatePathIds(fileId, routeId, flightId);
        if (flightServiceDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New FlightService expected. Identifier %s got", flightServiceDto.getId()));
        }

        // Set relationship ids
        flightServiceDto.setFlightId(flightId);

        // Calculate VAT
        calculateVat(fileId, routeId, flightId, flightServiceDto);

        FlightService flightService = IFlightServiceMapper.INSTANCE.toEntity(flightServiceDto);
        flightService = flightServiceRepository.save(flightService);

        return IFlightServiceMapper.INSTANCE.toDto(flightService);
    }

    @Override
    public FlightServiceDto updateFlightService(Long fileId, Long routeId, Long flightId, Long id, FlightServiceDto flightServiceDto) {
        validatePathIds(fileId, routeId, flightId);
        FlightService flightService = flightServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FlightService not found with id: " + id));

        // Recalculate vat
        calculateVat(fileId, routeId, flightId, flightServiceDto);

        IFlightServiceMapper.INSTANCE.updateFromDto(flightServiceDto, flightService);
        flightService = flightServiceRepository.save(flightService);

        return IFlightServiceMapper.INSTANCE.toDto(flightService);
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
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
        if (!routeRepository.existsById(routeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId);
        }
        if (!flightRepository.existsById(flightId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + flightId);
        }
    }

    private void calculateVat(final Long fileId, final Long routeId, final Long flightId, final FlightServiceDto flightServiceDto) {
        final Route route = routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No route found with id: " + routeId));
        final Flight flight = flightRepository.findById(flightId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No flight found with id: " + flightId));
        final Map<String, Airport> airportMap = route.getAirports().stream()
                .map(RouteAirport::getAirport)
                .distinct()
                .collect(Collectors.toMap(Airport::getIataCode, airport -> airport));
        final Airport origin = airportMap.get(flight.getOrigin());
        final Airport destination = airportMap.get(flight.getDestination());

        Double taxOnSale = calculationService.calculateFinalTaxToApply(fileId, origin, destination, flightServiceDto.getServiceType(), true);
        Double taxOnPurchase = calculationService.calculateServiceTaxToApply(fileId, origin, destination, flightServiceDto.getServiceType(), false);

        // Update dto values
        flightServiceDto.setTaxOnSale(taxOnSale);
        flightServiceDto.setTaxOnPurchase(taxOnPurchase);
    }
}
