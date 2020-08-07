package com.europair.management.rest.fleet.service;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.dto.AircraftDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AircraftService {

    Page<AircraftDto> findAllPaginated(Pageable pageable);

    Page<AircraftDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    Page<AircraftDto> findAllPaginatedByBasicFilter(Pageable pageable, String filter);

    AircraftDto findById(Long id);
}
