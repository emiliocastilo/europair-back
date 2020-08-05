package com.europair.management.rest.fleet.service;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.dto.AircraftDto;
import com.europair.management.rest.model.fleet.dto.AircraftFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AircraftService {

    Page<AircraftDto> findAllPaginated(Pageable pageable);

    List<AircraftDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    AircraftDto findById(Long id);
}
