package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFlightService {

    Page<FlightDTO> findAllPaginated(Pageable pageable);

    FlightDTO findById(Long id);

    FlightDTO saveFlight(FlightDTO flightDTO);

    FlightDTO updateFlight(Long id, FlightDTO flightDTO);

    void deleteFlight(Long id);

}
