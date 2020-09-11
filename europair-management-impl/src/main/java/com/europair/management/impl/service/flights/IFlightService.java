package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFlightService {

    Page<FlightDTO> findAllPaginated(Long fileId, Long routeId, Pageable pageable);

    FlightDTO findById(Long fileId, Long routeId, Long id);

    FlightDTO saveFlight(Long fileId, Long routeId, FlightDTO flightDTO);

    FlightDTO updateFlight(Long fileId, Long routeId, Long id, FlightDTO flightDTO);

    void deleteFlight(Long fileId, Long routeId, Long id);

}
