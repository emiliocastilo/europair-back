package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AircraftTypeRepositoryImpl extends BaseRepositoryImpl<AircraftType> implements IAircraftTypeRepositoryCustom {

    @Override
    public Page<AircraftType> findAircraftTypeByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageActiveByCriteria(AircraftType.class, criteria, pageable);
    }
}
