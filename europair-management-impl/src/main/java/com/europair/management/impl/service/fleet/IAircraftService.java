package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAircraftService {

    Page<AircraftDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    AircraftDto findById(Long id);

    AircraftDto saveAircraft(AircraftDto aircraftDto);

    AircraftDto updateAircraft(Long id, AircraftDto aircraftDto);

    void deleteAircraft(Long id);

    List<AircraftDto> searchAircraft(AircraftFilterDto filterDto);
}
