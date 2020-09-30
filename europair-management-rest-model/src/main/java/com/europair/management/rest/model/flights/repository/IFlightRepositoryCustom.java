package com.europair.management.rest.model.flights.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.flights.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IFlightRepositoryCustom {

    Page<Flight> findFlightByCriteria(CoreCriteria criteria, Pageable pageable);
}
