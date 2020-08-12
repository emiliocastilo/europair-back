package com.europair.management.rest.model.fleet.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AircraftCategoryRepositoryImpl extends BaseRepositoryImpl<AircraftCategory> implements IAircraftCategoryRepositoryCustom {

    @Override
    public Page<AircraftCategory> findAircraftCategoriesByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(AircraftCategory.class, criteria, pageable);
    }

}
