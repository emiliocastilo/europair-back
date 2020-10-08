package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightServiceDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFlightServiceService {

    Page<FlightServiceDto> findAllPaginatedByFilter(Long fileId, Long routeId, Long flightId, Pageable pageable, CoreCriteria criteria);

    FlightServiceDto findById(Long fileId, Long routeId, Long flightId, Long id);

    List<FlightServiceDto> saveFlightService(Long fileId, Long routeId, FlightServiceDto flightServiceDto);

    void updateFlightService(Long fileId, Long routeId, Long flightId, Long id, FlightServiceDto flightServiceDto);

    void deleteFlightService(Long fileId, Long routeId, Long flightId, Long id);
}
