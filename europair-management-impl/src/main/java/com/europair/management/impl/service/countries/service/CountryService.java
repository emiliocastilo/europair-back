package com.europair.management.impl.service.countries.service;


import com.europair.management.api.dto.countries.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {

    Page<CountryDTO> findAllPaginated(Pageable pageable);

    CountryDTO findById(Long id);

    CountryDTO saveCountry(CountryDTO countryDTO);

    CountryDTO updateCountry(Long id, CountryDTO countryDTO);

    void deleteCountry(Long id);

}
