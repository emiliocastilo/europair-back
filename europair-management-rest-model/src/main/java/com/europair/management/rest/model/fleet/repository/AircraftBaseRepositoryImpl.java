package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AircraftBaseRepositoryImpl extends BaseRepositoryImpl<AircraftBase> implements IAircraftBaseRepositoryCustom {

    @Override
    public Page<AircraftBase> findAircraftBaseByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(AircraftBase.class, criteria, pageable);
    }
}
