package com.europair.management.rest.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AircraftRepositoryCustom {
    List<Aircraft> findAircraftsByCriteria(CoreCriteria criteria, Pageable pageable);

    Long countAircraftsByCriteria(CoreCriteria coreCriteria, Pageable pageable);
}
