package com.europair.management.rest.model.cities.repository;

import com.europair.management.rest.model.cities.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
}
