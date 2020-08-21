package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAircraftTypeService {

    Page<AircraftTypeDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    AircraftTypeDto findById(Long id);

    AircraftTypeDto saveAircraftType(AircraftTypeDto aircraftTypeDto);

    AircraftTypeDto updateAircraftType(Long id, AircraftTypeDto aircraftTypeDto);

    void deleteAircraftType(Long id);
}
