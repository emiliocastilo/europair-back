package com.europair.management.rest.model.regions.repository;

import com.europair.management.rest.model.regions.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>, IRegionRepositoryCustom {

  //nothing here just only to have .save .delete .findOne .count .findAll , etc
}
