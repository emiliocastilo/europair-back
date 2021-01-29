package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long>, IAirportRepositoryCustom {

    List<Airport> findByRemovedAtNullAndLatitudeNotNullAndLongitudeNotNull();

    Optional<Airport> findFirstByIataCode(String code);

    Set<Airport> findByIdIn(@NotEmpty Set<Long> ids);

    boolean existsByIataCode(String code);

    boolean existsByIcaoCode(String code);
}
