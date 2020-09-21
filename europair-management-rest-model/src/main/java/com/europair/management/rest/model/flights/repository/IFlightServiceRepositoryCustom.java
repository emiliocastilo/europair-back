package com.europair.management.rest.model.flights.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.flights.entity.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IFlightServiceRepositoryCustom {

    Page<FlightService> findFlightServiceByCriteria(CoreCriteria criteria, Pageable pageable);
}
