package com.europair.management.rest.model.operatorsairports.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IOperatorsAirportsRepositoryCustom {

    Page<OperatorsAirports> findOperatorsAirportsByCriteria(CoreCriteria criteria, Pageable pageable);
}
