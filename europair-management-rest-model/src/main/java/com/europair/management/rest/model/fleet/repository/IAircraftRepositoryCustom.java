package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IAircraftRepositoryCustom {

    Page<Aircraft> findAircraftsByCriteria(CoreCriteria criteria, Pageable pageable);

    List<Aircraft> searchAircraft(CoreCriteria criteria);
}
