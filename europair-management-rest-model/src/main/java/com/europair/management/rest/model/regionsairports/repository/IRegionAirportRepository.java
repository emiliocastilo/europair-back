package com.europair.management.rest.model.regionsairports.repository;

import com.europair.management.rest.model.regionsairports.entity.RegionAirport;
import com.europair.management.rest.model.regionsairports.entity.RegionAirportPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionAirportRepository extends JpaRepository<RegionAirport, RegionAirportPK> {

    Page<RegionAirport> findByAirportId(Long airportId, Pageable pageable);

  //nothing here just only to have .save .delete .findOne .count .findAll , etc
}
