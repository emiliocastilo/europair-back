package com.europair.management.rest.model.regionscountries.repository;

import com.europair.management.rest.model.regions.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionRepository extends JpaRepository<Region, Long> {

  //nothing here just only to have .save .delete .findOne .count .findAll , etc
}
