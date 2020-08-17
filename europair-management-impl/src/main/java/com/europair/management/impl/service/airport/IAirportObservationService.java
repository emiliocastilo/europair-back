package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.AirportObservationDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAirportObservationService {

    Page<AirportObservationDto> findAllPaginatedByFilter(Long airportId, Pageable pageable, CoreCriteria criteria);

    AirportObservationDto findById(Long airportId, Long id);

    AirportObservationDto saveAirportObservation(Long airportId, AirportObservationDto airportObservationDto);

    AirportObservationDto updateAirportObservation(Long airportId, Long id, AirportObservationDto airportObservationDto);

    void deleteAirportObservation(Long airportId, Long id);
}
