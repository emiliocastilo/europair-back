package com.europair.management.rest.model.countries.repository;

import com.europair.management.rest.model.countries.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>, ICountryRepositoryCustom {

    Optional<Country> findByCode(final String code);

}