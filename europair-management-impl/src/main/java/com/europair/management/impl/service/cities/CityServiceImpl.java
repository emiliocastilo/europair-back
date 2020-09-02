package com.europair.management.impl.service.cities;

import com.europair.management.api.dto.cities.CityDTO;
import com.europair.management.api.dto.countries.CountryDTO;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.cities.CityMapper;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.cities.repository.CityRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements ICityService {

    private final CityRepository cityRepository;

    @Override
    public Page<CityDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return cityRepository.findCityByCriteria(criteria, pageable).map(CityMapper.INSTANCE::toDto);
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
        CityDTO dto = new CityDTO();
        dto.setId(id);
        dto.setCode(cityDTO.getCode());
        dto.setName(cityDTO.getName());

        CountryDTO country = new CountryDTO();
        country.setId(cityDTO.getCountry().getId());
        dto.setCountry(country);
        return dto;
    }
}
