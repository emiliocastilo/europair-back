package com.europair.management.rest.model.flights.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.flights.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FlightRepositoryImpl extends BaseRepositoryImpl<Flight> implements IFlightRepositoryCustom {

    @Override
    public Page<Flight> findFlightByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Flight.class, criteria, pageable);
    }
}
