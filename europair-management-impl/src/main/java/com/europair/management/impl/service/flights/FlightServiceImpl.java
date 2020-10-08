package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.impl.mappers.flights.IFlightMapper;
import com.europair.management.impl.util.Utils;
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
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
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


    @Override
    public Page<FlightDTO> findAllPaginated(final Long fileId, final Long routeId, Pageable pageable, CoreCriteria criteria) {
      checkIfFileExists(fileId);
      Route route = routeRepository.findById(routeId).orElseThrow(() ->
              new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));

        Utils.addCriteriaIfNotExists(criteria, FILE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(fileId));

        if (route.getParentRoute() == null) {
            Utils.addCriteriaIfNotExists(criteria, PARENT_ROUTE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(routeId));
        } else {
            Utils.addCriteriaIfNotExists(criteria, ROUTE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(routeId));
        }

      return flightRepository.findFlightByCriteria(criteria, pageable).map(IFlightMapper.INSTANCE::toDto);
    }

    @Override
    public FlightDTO findById(final Long fileId, final Long routeId, final Long id) {
      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      return IFlightMapper.INSTANCE.toDto(flightRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = false)
    public FlightDTO saveFlight(final Long fileId, final Long routeId, final FlightDTO flightDTO) {

      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      if (flightDTO.getId() != null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New flight expected. Identifier %s got", flightDTO.getId()));
      }
      Flight flight = IFlightMapper.INSTANCE.toEntity(flightDTO);
      flight.setRouteId(routeId);

      flight = flightRepository.save(flight);

      return IFlightMapper.INSTANCE.toDto(flight);
    }

    @Override
    @Transactional(readOnly = false)
    public FlightDTO updateFlight(final Long fileId, final Long routeId, final Long id, final FlightDTO flightDTO) {

      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      Flight flight = flightRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + id));

      IFlightMapper.INSTANCE.updateFromDto(flightDTO, flight);
      flight = flightRepository.save(flight);

      return IFlightMapper.INSTANCE.toDto(flight);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFlight(final Long fileId, final Long routeId, final Long id) {

      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      if (!flightRepository.existsById(id)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + id);
      }
      flightRepository.deleteById(id);
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

}
