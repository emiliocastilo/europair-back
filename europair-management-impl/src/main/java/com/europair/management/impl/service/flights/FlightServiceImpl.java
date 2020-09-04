package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.flights.IFlightMapper;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.repository.IFlightRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private FileRepository fileRepository;


    @Override
    public Page<FlightDTO> findAllPaginated(final Long fileId, final Long routeId, Pageable pageable) {
      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      return flightRepository.findAll(pageable).map(IFlightMapper.INSTANCE::toDto);
    }

    @Override
    public FlightDTO findById(final Long fileId, final Long routeId, final Long id) {
      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      return IFlightMapper.INSTANCE.toDto(flightRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = false)
    public FlightDTO saveFlight(final Long fileId, final Long routeId, final FlightDTO flightDTO) {

      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      if (flightDTO.getId() != null) {
        throw new InvalidArgumentException(String.format("New flight expected. Identifier %s got", flightDTO.getId()));
      }
      Flight flight = IFlightMapper.INSTANCE.toEntity(flightDTO);

      Route route = new Route();
      route.setId(routeId);
      flight.setRoute(route);

      flight = flightRepository.save(flight);

      return IFlightMapper.INSTANCE.toDto(flight);
    }

    @Override
    @Transactional(readOnly = false)
    public FlightDTO updateFlight(final Long fileId, final Long routeId, final Long id, final FlightDTO flightDTO) {

      checkIfFileExists(fileId);
      checkIfRouteExists(routeId);

      Flight flight = flightRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));

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
        throw new ResourceNotFoundException("Flight not found with id: " + id);
      }
      flightRepository.deleteById(id);
    }


    private void checkIfFileExists(final Long fileId) {
      if (!fileRepository.existsById(fileId)) {
        throw new ResourceNotFoundException("File not found with id: " + fileId);
      }
    }

    private void checkIfRouteExists(final Long routeId) {
      if (!routeRepository.existsById(routeId)) {
        throw new ResourceNotFoundException("Route not found with id: " + routeId);
      }
    }

}
