package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long>, IAircraftRepositoryCustom {
}
