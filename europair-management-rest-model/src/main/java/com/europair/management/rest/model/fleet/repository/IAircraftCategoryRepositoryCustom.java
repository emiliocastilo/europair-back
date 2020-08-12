package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IAircraftCategoryRepositoryCustom {

    Page<AircraftCategory> findAircraftCategoriesByCriteria(CoreCriteria criteria, Pageable pageable);

    Long countAircraftCategoriesByCriteria(CoreCriteria criteria);
}
