package com.europair.management.rest.model.flights.repository;

import com.europair.management.rest.model.flights.entity.FlightService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightServiceRepository extends JpaRepository<FlightService, Long>, IFlightServiceRepositoryCustom {

    List<FlightService> findAllByFlightId(Long flightId);

}
