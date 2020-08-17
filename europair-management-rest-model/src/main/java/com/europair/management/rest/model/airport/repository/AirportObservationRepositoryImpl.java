package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.AirportObservation;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AirportObservationRepositoryImpl extends BaseRepositoryImpl<AirportObservation> implements IAirportObservationRepositoryCustom {

    @Override
    public Page<AirportObservation> findAirportObservationByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(AirportObservation.class, criteria, pageable);
    }
}
