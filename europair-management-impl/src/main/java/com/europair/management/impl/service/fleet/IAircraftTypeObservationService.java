package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeObservationDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAircraftTypeObservationService {

    Page<AircraftTypeObservationDto> findAllPaginatedByFilter(Long aircraftTypeId, Pageable pageable, CoreCriteria criteria);

    AircraftTypeObservationDto findById(Long aircraftTypeId, Long id);

    AircraftTypeObservationDto saveAircraftTypeObservation(Long aircraftTypeId, AircraftTypeObservationDto aircraftTypeObservationDto);

    AircraftTypeObservationDto updateAircraftTypeObservation(Long aircraftTypeId, Long id, AircraftTypeObservationDto aircraftTypeObservationDto);

    void deleteAircraftTypeObservation(Long aircraftTypeId, Long id);
}
