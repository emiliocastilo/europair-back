package com.europair.management.rest.model.regionscountries.repository;

import com.europair.management.rest.model.regionscountries.entity.RegionCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionCountryRepository extends JpaRepository<RegionCountry, Long> {

  //nothing here just only to have .save .delete .findOne .count .findAll , etc
}
