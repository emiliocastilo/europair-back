package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public interface IAircraftService {

    Page<AircraftDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    AircraftDto findById(Long id);

    AircraftDto saveAircraft(AircraftDto aircraftDto);

    AircraftDto updateAircraft(Long id, AircraftDto aircraftDto);

    void deleteAircraft(Long id);

    void reactivateAircraft(@NotEmpty Set<Long> aircraftIds);
}
