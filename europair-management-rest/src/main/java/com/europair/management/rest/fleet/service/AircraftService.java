package com.europair.management.rest.fleet.service;

import com.europair.management.rest.model.fleet.dto.AircraftDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AircraftService {

    Page<AircraftDto> findAllPaginated(Pageable pageable);
    AircraftDto findById(Long id);
}
