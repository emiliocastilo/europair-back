package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.Runway;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class RunwayRepositoryImpl extends BaseRepositoryImpl<Runway> implements IRunwayRepositoryCustom {

    @Override
    public Page<Runway> findRunwayByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Runway.class, criteria, pageable);
    }
}
