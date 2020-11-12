package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.impl.mappers.flights.IFlightMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.repository.FlightRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightServiceImpl implements IFlightService {

    private final String FILE_ID_FILTER = "route.file.id";
    private final String ROUTE_ID_FILTER = "route.id";
    private final String PARENT_ROUTE_ID_FILTER = "route.parentRoute.id";

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AirportRepository airportRepository;


    @Override
    public Page<FlightDTO> findAllPaginated(Pageable pageable, CoreCriteria criteria) {
      return flightRepository.findFlightByCriteria(criteria, pageable).map(IFlightMapper.INSTANCE::toDto);
    }

    @Override
    public FlightDTO findById(final Long id) {
      return IFlightMapper.INSTANCE.toDto(flightRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + id)));
    }

    @Override
    public Page<FlightDTO> findAllPaginated(final Long fileId, final Long routeId, Pageable pageable, CoreCriteria criteria) {
      checkIfFileExists(fileId);
      Route route = getRoute(routeId);

      Utils.addCriteriaIfNotExists(criteria, FILE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(fileId));

      if (route.getParentRoute() == null) {
          Utils.addCriteriaIfNotExists(criteria, PARENT_ROUTE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(routeId));
      } else {
          Utils.addCriteriaIfNotExists(criteria, ROUTE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(routeId));
      }

      return findAllPaginated(pageable, criteria);
    }

    @Override
    public FlightDTO findById(final Long fileId, final Long routeId, final Long id) {
      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      return findById(id);
    }

    @Override
    public FlightDTO saveFlight(final Long fileId, final Long routeId, final FlightDTO flightDTO) {

      checkIfFileExists(fileId);
      Route rotation = getRoute(routeId);

      if (flightDTO.getId() != null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New flight expected. Identifier %s got", flightDTO.getId()));
      }
      Flight flight = IFlightMapper.INSTANCE.toEntity(flightDTO);
      flight.setRouteId(routeId);
      // Add flight in last order
      flight.setOrder(rotation.getFlights().stream()
              .max(Comparator.comparing(Flight::getOrder))
              .map(Flight::getOrder)
              .orElse(0) + 1);

      flight.setArrivalTime(flight.getDepartureTime());

      flight = flightRepository.save(flight);
      updateRotationData(routeId);

      return IFlightMapper.INSTANCE.toDto(flight);
    }

    @Override
    public void updateFlight(final Long id, final FlightDTO flightDTO) {
      Flight flight = flightRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + id));

      IFlightMapper.INSTANCE.updateFromDto(flightDTO, flight);
      flight = flightRepository.save(flight);
    }

    @Override
    public void updateFlight(final Long fileId, final Long routeId, final Long id, final FlightDTO flightDTO) {

      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      updateFlight(id, flightDTO);
      updateRotationData(routeId);
    }

    @Override
    public void deleteFlight(final Long fileId, final Long routeId, final Long id) {

      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      if (!flightRepository.existsById(id)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + id);
      }
      flightRepository.deleteById(id);
      updateRotationData(routeId);
    }

    @Override
    public void updateFlightsOrder(Long fileId, Long routeId, List<FlightDTO> flights) {
        checkIfFileExists(fileId);
        Route rotation = getRoute(routeId);
        if (CollectionUtils.isEmpty(rotation.getFlights())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "No flights found for route with id: " + routeId);
        }
        Map<Long, Integer> flightIdOrderMap = flights.stream()
                .collect(Collectors.toMap(FlightDTO::getId, FlightDTO::getOrder));
        List<Flight> updatedFlights = rotation.getFlights().stream().map(flight -> {
            flight.setOrder(flightIdOrderMap.get(flight.getId()));
            return flight;
        }).collect(Collectors.toList());
        updatedFlights = flightRepository.saveAll(updatedFlights);
        updateRotationData(routeId);
    }

    private void checkIfFileExists(final Long fileId) {
      if (!fileRepository.existsById(fileId)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
      }
    }

    private void checkIfRouteExists(final Long routeId) {
      if (!routeRepository.existsById(routeId)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId);
      }
    }

    private Route getRoute(final Long routeId) {
        return routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));
    }

    private Airport getAirport(final Long airportId) {
        return airportRepository.findById(airportId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found with id: " + airportId));
    }

    private void updateRotationDates(final Route rotation, final List<Flight> flights) {
        LocalDateTime minDate = flights.stream()
                .min(Comparator.comparing(Flight::getDepartureTime))
                .map(Flight::getDepartureTime)
                .orElse(null);
        LocalDateTime maxDate = flights.stream()
                .max(Comparator.comparing(Flight::getDepartureTime))
                .map(flight -> flight.getArrivalTime() != null ? flight.getArrivalTime() : flight.getDepartureTime())
                .orElse(null);
        if (minDate != null && !minDate.toLocalDate().equals(rotation.getStartDate())) {
            rotation.setStartDate(minDate.toLocalDate());
        }
        if (maxDate != null && !maxDate.toLocalDate().equals(rotation.getEndDate())) {
            rotation.setEndDate(maxDate.toLocalDate());
        }
    }

    private void updateRotationLabel(final Route rotation, final List<Flight> flights) {
        final String AIRPORT_SEPARATOR = "-";
        final String SPECIAL_FLIGHT_SEPARATOR = " // ";
        StringBuilder sb = new StringBuilder();
        Flight previousFlight = null;
        for (int i = 0; i < flights.size(); i++) {
            Flight f = flights.get(i);
            Airport origin = f.getOrigin() != null ? f.getOrigin() : getAirport(f.getOriginId());
            Airport destination = f.getDestination() != null ? f.getDestination() : getAirport(f.getDestinationId());
            if (i == 0) {
                // First flight
                sb.append(origin.getIataCode()).append(AIRPORT_SEPARATOR).append(destination.getIataCode());
            } else {
                if (previousFlight.getDestinationId().equals(f.getOriginId())) {
                    // Connected flight
                    sb.append(AIRPORT_SEPARATOR).append(destination.getIataCode());
                } else {
                    // Special unconnected flight
                    sb.append(SPECIAL_FLIGHT_SEPARATOR).append(origin.getIataCode()).append(AIRPORT_SEPARATOR).append(destination.getIataCode());
                }
            }
            previousFlight = f;
        }
        rotation.setLabel(sb.toString());
    }

    private void updateRotationData(final Long routeId) {
        Route rotation = getRoute(routeId);
        List<Flight> flights = flightRepository.findAllByRouteId(routeId);
        flights.sort(Comparator.comparing(Flight::getOrder));

        updateRotationDates(rotation, flights);
        updateRotationLabel(rotation, flights);
        rotation = routeRepository.save(rotation);
        updateRouteData(rotation.getParentRouteId());
    }

    private void updateRouteData(final Long routeId) {
        Route route = getRoute(routeId);
        List<Route> updatedRotations = routeRepository.findByParentRouteId(routeId);
        LocalDate minDate = updatedRotations.stream()
                .min(Comparator.comparing(Route::getStartDate))
                .map(Route::getStartDate)
                .orElse(null);
        LocalDate maxDate = updatedRotations.stream()
                .max(Comparator.comparing(Route::getEndDate))
                .map(Route::getEndDate)
                .orElse(null);
        if (minDate != null && !minDate.equals(route.getStartDate())) {
            route.setStartDate(minDate);
        }
        if (maxDate != null && !maxDate.equals(route.getEndDate())) {
            route.setEndDate(maxDate);
        }
        route = routeRepository.save(route);
    }

}
