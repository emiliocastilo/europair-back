package com.europair.management.impl.service.countries;

import com.europair.management.api.dto.countries.CountryDTO;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.countries.CountryMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.countries.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryServiceImpl implements ICountryService {

    private final CountryRepository countryRepository;

    @Override
    public Page<CountryDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return countryRepository.findCountryByCriteria(criteria, pageable).map(CountryMapper.INSTANCE::toDto);
    }

    @Override
    public CountryDTO findById(Long id) throws ResourceNotFoundException {
        return CountryMapper.INSTANCE.toDto(countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found on id: " + id)));
    }

    @Override
    public CountryDTO saveCountry(final CountryDTO countryDTO) {
        Country country = countryRepository.save(CountryMapper.INSTANCE.toEntity(countryDTO));
        return CountryMapper.INSTANCE.toDto(country);
    }

    @Override
    public CountryDTO updateCountry(final Long id, final CountryDTO countryDTO) {

        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found on id: " + id));

        CountryMapper.INSTANCE.updateFromDTO(countryDTO, country);
        country = countryRepository.save(country);

        return CountryMapper.INSTANCE.toDto(country);
    }

    @Override
    public void deleteCountry(Long id) {

        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found on id: " + id));
        countryRepository.delete(country);
    }

}
