package com.europair.management.rest.conversions.repository;

import com.europair.management.rest.model.conversions.entity.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
}
