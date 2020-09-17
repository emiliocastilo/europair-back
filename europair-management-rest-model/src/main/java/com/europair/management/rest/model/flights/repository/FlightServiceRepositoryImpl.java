package com.europair.management.rest.model.flights.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.flights.entity.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FlightServiceRepositoryImpl extends BaseRepositoryImpl<FlightService> implements IFlightServiceRepositoryCustom {

    @Override
    public Page<FlightService> findFlightServiceByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(FlightService.class, criteria, pageable);
    }
}
