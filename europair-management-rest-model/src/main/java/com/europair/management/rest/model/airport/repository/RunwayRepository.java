package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.Runway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunwayRepository extends JpaRepository<Runway, Long>, IRunwayRepositoryCustom {

    List<Runway> findAllByAirportId(Long aiportId);
}
