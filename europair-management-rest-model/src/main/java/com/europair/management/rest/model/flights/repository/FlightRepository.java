package com.europair.management.rest.model.flights.repository;

import com.europair.management.rest.model.flights.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>, IFlightRepositoryCustom {
}
