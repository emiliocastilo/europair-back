package com.europair.management.rest.fleet.service;

import com.europair.management.rest.model.fleet.dto.AircraftCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AircraftCategoryService {

    AircraftCategoryDto saveAircraftCategory(AircraftCategoryDto aircraftCategoryDto);

    AircraftCategoryDto updateAircraftCategory(Long id, AircraftCategoryDto aircraftCategoryDto);

    void deleteAircraftCategory(Long id);

    AircraftCategoryDto findById(Long id);

    Page<AircraftCategoryDto> findAllPaginated(Pageable pageable);
}
