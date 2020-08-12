package com.europair.management.impl.service.cities;


import com.europair.management.api.dto.cities.CityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICityService {

  Page<CityDTO> findAllPaginated(Pageable pageable);
  CityDTO findById(Long id);
  CityDTO saveCity(CityDTO cityDTO);
  CityDTO updateCity(Long id, CityDTO cityDTO);
  void deleteCity(Long id);

}
