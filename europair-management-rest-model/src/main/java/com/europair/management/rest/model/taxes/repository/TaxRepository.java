package com.europair.management.rest.model.taxes.repository;

import com.europair.management.rest.model.taxes.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

    Optional<Tax> findFirstByCode(String code);

}
