package com.europair.management.rest.model.routes.repository;

import com.europair.management.rest.model.routes.entity.RouteAirport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteAirportRepository extends JpaRepository<RouteAirport, Long> {
}
