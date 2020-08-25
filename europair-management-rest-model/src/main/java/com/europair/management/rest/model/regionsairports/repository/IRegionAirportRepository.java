package com.europair.management.rest.model.regionsairports.repository;

import com.europair.management.rest.model.regionsairports.entity.RegionAirport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionAirportRepository extends JpaRepository<RegionAirport, Long> {

  //nothing here just only to have .save .delete .findOne .count .findAll , etc
}
