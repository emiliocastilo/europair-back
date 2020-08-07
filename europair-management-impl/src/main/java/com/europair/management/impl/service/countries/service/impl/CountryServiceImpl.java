package com.europair.management.impl.service.countries.service.impl;

import com.europair.management.api.dto.countries.dto.CountryDTO;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.countries.CountryMapper;
import com.europair.management.impl.service.countries.service.ICountryService;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.countries.repository.ICountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryServiceImpl implements ICountryService {

    private final ICountryRepository countryRepository;

    @Override
    public Page<CountryDTO> findAllPaginated(Pageable pageable) {
        return countryRepository.findAll(pageable).map(country -> CountryMapper.INSTANCE.toDto(country));
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

        CountryDTO storedCountryDTO = CountryMapper.INSTANCE.toDto(countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found on id: " + id)));

        CountryDTO updatedCountryDTO = updateCountryValues(storedCountryDTO.getId(), countryDTO);

        Country country = countryRepository.save(CountryMapper.INSTANCE.toEntity(updatedCountryDTO));

        return CountryMapper.INSTANCE.toDto(country);
    }

    @Override
    public void deleteCountry(Long id) {

        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found on id: " + id));
        countryRepository.delete(country);
    }

    private CountryDTO updateCountryValues(Long id, CountryDTO countryDTO) {

        return CountryDTO.builder()
                .id(id)
                .code(countryDTO.getCode())
                .name(countryDTO.getName())
                .build();

    }

}
