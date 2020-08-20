package com.europair.management.rest.model.operatorsairports.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OperatorsAirportsRepositoryImpl extends BaseRepositoryImpl<OperatorsAirports> implements IOperatorsAirportsRepositoryCustom {

    @Override
    public Page<OperatorsAirports> findOperatorsAirportsByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageActiveByCriteria(OperatorsAirports.class, criteria, pageable);
    }
}
