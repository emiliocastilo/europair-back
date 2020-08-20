package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftBaseDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAircraftBaseService {

    Page<AircraftBaseDto> findAllPaginatedByFilter(Long aircraftId, Pageable pageable, CoreCriteria criteria);

    AircraftBaseDto findById(Long aircraftId, Long id);

    AircraftBaseDto saveAircraftBase(Long aircraftId, AircraftBaseDto aircraftBaseDto);

    AircraftBaseDto updateAircraftBase(Long aircraftId, Long id, AircraftBaseDto aircraftBaseDto);

    void deleteAircraftBase(Long aircraftId, Long id);
}
