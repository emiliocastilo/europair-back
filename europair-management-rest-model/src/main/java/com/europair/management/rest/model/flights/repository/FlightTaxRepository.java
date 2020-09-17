package com.europair.management.rest.model.flights.repository;

import com.europair.management.rest.model.flights.entity.FlightTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightTaxRepository extends JpaRepository<FlightTax, Long> {
}
