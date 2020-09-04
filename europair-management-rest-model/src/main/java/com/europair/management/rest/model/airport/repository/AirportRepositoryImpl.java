package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AirportRepositoryImpl extends BaseRepositoryImpl<Airport> implements IAirportRepositoryCustom {

    @Override
    public Page<Airport> findAirportByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Airport.class, criteria, pageable);
    }
}
