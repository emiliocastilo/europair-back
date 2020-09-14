package com.europair.management.rest.model.taxes.repository;

import com.europair.management.rest.model.taxes.entity.RouteBalearicsPctVat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRouteBalearicsPctVatRepository extends JpaRepository<RouteBalearicsPctVat, Long> {

  Optional<RouteBalearicsPctVat> findByOriginAirportIdAndDestinationAirportId(Long originAirportId, Long destinationAirportId);

}
