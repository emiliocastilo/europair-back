package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.fleet.entity.AircraftTypeObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftTypeObservationRepository extends JpaRepository<AircraftTypeObservation, Long>, IAircraftTypeObservationRepositoryCustom {
}
