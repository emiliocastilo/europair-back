package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AircraftRepositoryCustom {
    Page<Aircraft> findAircraftsByCriteria(CoreCriteria criteria, Pageable pageable);

    Long countAircraftsByCriteria(CoreCriteria coreCriteria);
}
