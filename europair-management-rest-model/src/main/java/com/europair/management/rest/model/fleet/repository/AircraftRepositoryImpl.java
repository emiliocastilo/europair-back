package com.europair.management.rest.model.fleet.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AircraftRepositoryImpl extends BaseRepositoryImpl<Aircraft> implements IAircraftRepositoryCustom {

    @Override
    public Page<Aircraft> findAircraftsByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Aircraft.class, criteria, pageable);
    }

}
