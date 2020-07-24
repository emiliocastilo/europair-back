package com.europair.management.rest.countries.service;

import com.europair.management.rest.model.countries.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {

    Page<CountryDTO> findAllPaginated(Pageable pageable);

    CountryDTO findById(Long id);

    CountryDTO saveCountry(CountryDTO countryDTO);

    CountryDTO updateCountry(Long id, CountryDTO countryDTO);

    void deleteCountry(Long id);

}
