package com.europair.management.rest.cities.service;

import com.europair.management.rest.model.cities.dto.CityDTO;
import com.europair.management.rest.model.countries.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

  Page<CityDTO> findAllPaginated(Pageable pageable);
  CityDTO findById(Long id);
  CityDTO saveCity(CityDTO cityDTO);
  CityDTO updateCity(Long id, CityDTO cityDTO);
  void deleteCity(Long id);

}
