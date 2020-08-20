package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.AircraftObservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IAircraftObservationRepositoryCustom {

    Page<AircraftObservation> findAircraftObservationByCriteria(CoreCriteria criteria, Pageable pageable);
}
