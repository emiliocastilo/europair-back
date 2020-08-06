package com.europair.management.rest.cities.service.impl;

import com.europair.management.rest.cities.repository.CityRepository;
import com.europair.management.rest.cities.service.CityService;
import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.cities.dto.CityDTO;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.cities.mapper.CityMapper;
import com.europair.management.rest.model.countries.dto.CountryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public Page<CityDTO> findAllPaginated(Pageable pageable) {
        return cityRepository.findAll(pageable).map(city -> CityMapper.INSTANCE.toDto(city));
    }

    @Override
    public CityDTO findById(Long id) throws ResourceNotFoundException {
        return CityMapper.INSTANCE.toDto(cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found on id: " + id)));
    }

    @Override
    public CityDTO saveCity(final CityDTO cityDTO) {
        City city = cityRepository.save(CityMapper.INSTANCE.toEntity(cityDTO));
        CityDTO cityDTO1 = CityMapper.INSTANCE.toDto(city);
        return cityDTO1;
    }

    @Override
    public CityDTO updateCity(final Long id, final CityDTO cityDTO) {

        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found on id: " + id));

        CityDTO dto2Update = updateCityValues(id, cityDTO);

        CityMapper.INSTANCE.updateFromDto(dto2Update, city);
        city = cityRepository.save(city);

        return CityMapper.INSTANCE.toDto(city);
    }

    @Override
    public void deleteCity(Long id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found on id: " + id));
        cityRepository.delete(city);
    }

    private CityDTO updateCityValues(Long id, CityDTO cityDTO) {

        return CityDTO.builder()
                .id(id)
                .code(cityDTO.getCode())
                .name(cityDTO.getName())
                .country(CountryDTO.builder().id(cityDTO.getCountry().getId()).build())
                .build();

    }
}
