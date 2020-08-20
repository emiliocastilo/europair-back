package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeAverageSpeedDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAircraftTypeAverageSpeedService {

    Page<AircraftTypeAverageSpeedDto> findAllPaginatedByFilter(Long aircraftTypeId, Pageable pageable, CoreCriteria criteria);

    AircraftTypeAverageSpeedDto findById(Long aircraftTypeId, Long id);

    AircraftTypeAverageSpeedDto saveAircraftTypeAverageSpeed(Long aircraftTypeId, AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto);

    AircraftTypeAverageSpeedDto updateAircraftTypeAverageSpeed(Long aircraftTypeId, Long id, AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto);

    void deleteAircraftTypeAverageSpeed(Long aircraftTypeId, Long id);
}
