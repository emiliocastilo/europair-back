package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long>, IAirportRepositoryCustom {

    List<Airport> findByRemovedAtNullAndLatitudeNotNullAndLongitudeNotNull();

    Optional<Airport> findFirstByIataCode(String code);
}
