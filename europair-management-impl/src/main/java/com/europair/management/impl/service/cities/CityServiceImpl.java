package com.europair.management.impl.service.cities;

import com.europair.management.api.dto.cities.CityDTO;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.cities.CityMapper;
import com.europair.management.impl.util.Utils;
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
    public CityDTO findById(Long id) {
        return CityMapper.INSTANCE.toDto(cityRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CITY_NOT_FOUND, String.valueOf(id))));
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
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CITY_NOT_FOUND, String.valueOf(id)));

        CityMapper.INSTANCE.updateFromDto(cityDTO, city);
        city = cityRepository.save(city);

        return CityMapper.INSTANCE.toDto(city);
    }

    @Override
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CITY_NOT_FOUND, String.valueOf(id)));
        cityRepository.delete(city);
    }

}
