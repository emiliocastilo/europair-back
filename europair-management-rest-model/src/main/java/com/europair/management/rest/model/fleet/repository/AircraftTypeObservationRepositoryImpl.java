package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.AircraftTypeObservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AircraftTypeObservationRepositoryImpl extends BaseRepositoryImpl<AircraftTypeObservation> implements IAircraftTypeObservationRepositoryCustom {

    @Override
    public Page<AircraftTypeObservation> findAircraftTypeObservationByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(AircraftTypeObservation.class, criteria, pageable);
    }
}
