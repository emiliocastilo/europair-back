package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.AircraftTypeAverageSpeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AircraftTypeAverageSpeedRepositoryImpl extends BaseRepositoryImpl<AircraftTypeAverageSpeed> implements IAircraftTypeAverageSpeedRepositoryCustom {

    @Override
    public Page<AircraftTypeAverageSpeed> findAircraftTypeAverageSpeedByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(AircraftTypeAverageSpeed.class, criteria, pageable);
    }
}
