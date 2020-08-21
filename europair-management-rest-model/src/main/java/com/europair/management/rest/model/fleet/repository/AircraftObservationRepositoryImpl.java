package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.AircraftObservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AircraftObservationRepositoryImpl extends BaseRepositoryImpl<AircraftObservation> implements IAircraftObservationRepositoryCustom {

    @Override
    public Page<AircraftObservation> findAircraftObservationByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(AircraftObservation.class, criteria, pageable);
    }
}
