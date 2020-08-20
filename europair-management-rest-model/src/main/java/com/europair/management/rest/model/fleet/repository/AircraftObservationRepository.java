package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.fleet.entity.AircraftObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftObservationRepository extends JpaRepository<AircraftObservation, Long>, IAircraftObservationRepositoryCustom {
}
