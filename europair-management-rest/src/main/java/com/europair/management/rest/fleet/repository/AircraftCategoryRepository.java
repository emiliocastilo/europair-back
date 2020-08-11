package com.europair.management.rest.fleet.repository;

import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftCategoryRepository extends JpaRepository<AircraftCategory, Long>, IAircraftCategoryRepositoryCustom {
}
