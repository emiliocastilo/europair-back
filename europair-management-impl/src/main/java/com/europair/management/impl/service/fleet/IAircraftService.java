package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.rest.model.common.CoreCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAircraftService {

    Page<AircraftDto> findAllPaginated(Pageable pageable);

    Page<AircraftDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    Page<AircraftDto> findAllPaginatedByBasicFilter(Pageable pageable, String filter);

    AircraftDto findById(Long id);

    AircraftDto saveAircraft(AircraftDto aircraftDto);

    AircraftDto updateAircraft(Long id, AircraftDto aircraftDto);

    void deleteAircraft(Long id);
}
