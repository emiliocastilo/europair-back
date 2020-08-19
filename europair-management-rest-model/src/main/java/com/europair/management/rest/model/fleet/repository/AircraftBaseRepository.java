package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.fleet.entity.AircraftBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftBaseRepository extends JpaRepository<AircraftBase, Long>, IAircraftBaseRepositoryCustom {
}
