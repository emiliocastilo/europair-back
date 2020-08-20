package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IAircraftBaseRepositoryCustom {

    Page<AircraftBase> findAircraftBaseByCriteria(CoreCriteria criteria, Pageable pageable);
}
