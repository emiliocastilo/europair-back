package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.AirportObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportObservationRepository extends JpaRepository<AirportObservation, Long>, IAirportObservationRepositoryCustom {
}
