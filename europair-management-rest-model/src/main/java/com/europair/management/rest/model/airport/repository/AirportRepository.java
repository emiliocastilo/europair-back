package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long>, IAirportRepositoryCustom {
}
