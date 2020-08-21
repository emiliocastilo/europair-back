package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftObservationDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAircraftObservationService {

    Page<AircraftObservationDto> findAllPaginatedByFilter(Long aircraftId, Pageable pageable, CoreCriteria criteria);

    AircraftObservationDto findById(Long aircraftId, Long id);

    AircraftObservationDto saveAircraftObservation(Long aircraftId, AircraftObservationDto aircraftObservationDto);

    AircraftObservationDto updateAircraftObservation(Long aircraftId, Long id, AircraftObservationDto aircraftObservationDto);

    void deleteAircraftObservation(Long aircraftId, Long id);
}
