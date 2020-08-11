package com.europair.management.rest.fleet.service;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.dto.AircraftCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AircraftCategoryService {

    AircraftCategoryDto saveAircraftCategory(AircraftCategoryDto aircraftCategoryDto);

    AircraftCategoryDto updateAircraftCategory(Long id, AircraftCategoryDto aircraftCategoryDto);

    void deleteAircraftCategory(Long id);

    AircraftCategoryDto findById(Long id);

    Page<AircraftCategoryDto> findAllPaginated(CoreCriteria criteria, Pageable pageable);

    AircraftCategoryDto saveAircraftSubcategory(Long parentCategoryId, AircraftCategoryDto aircraftCategoryDto);

    AircraftCategoryDto updateAircraftSubcategory(Long parentCategoryId, Long id, AircraftCategoryDto aircraftCategoryDto);

    void deleteAircraftSubcategory(Long parentCategoryId, Long id);

    AircraftCategoryDto findSubcategoryById(Long parentCategoryId, Long id);

    Page<AircraftCategoryDto> findAllSubcategoriesPaginated(Long parentCategoryId, CoreCriteria criteria, Pageable pageable);
}
