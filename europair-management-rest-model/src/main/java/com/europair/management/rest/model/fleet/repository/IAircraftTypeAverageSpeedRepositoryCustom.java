package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.AircraftTypeAverageSpeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IAircraftTypeAverageSpeedRepositoryCustom {

    Page<AircraftTypeAverageSpeed> findAircraftTypeAverageSpeedByCriteria(CoreCriteria criteria, Pageable pageable);
}
