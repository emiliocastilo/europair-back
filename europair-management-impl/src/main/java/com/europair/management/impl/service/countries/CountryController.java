package com.europair.management.impl.service.countries;


import com.europair.management.api.dto.countries.CountryDTO;
import com.europair.management.api.service.countries.ICountryController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CountryController implements ICountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    private final ICountryService countryService;

    public ResponseEntity<Page<CountryDTO>> getAllCountriesPaginated(final Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[CountryController] - Starting method [getAllCountriesPaginated] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<CountryDTO> countryDTOPage = countryService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[CountryController] - Ending method [getAllCountriesPaginated] with return: {}", countryDTOPage);
        return ResponseEntity.ok().body(countryDTOPage);
    }

    public ResponseEntity<CountryDTO> getCountryById(@NotNull final Long id) {
        LOGGER.debug("[CountryController] - Starting method [getCountryById] with input: id={}", id);
        final CountryDTO countryDTO = countryService.findById(id);
        LOGGER.debug("[CountryController] - Ending method [getCountryById] with return: {}", countryDTO);
        return ResponseEntity.ok().body(countryDTO);
    }

    public ResponseEntity<CountryDTO> saveCountry(final CountryDTO countryDTO) {
        LOGGER.debug("[CountryController] - Starting method [saveCountry] with input: countryDTO={}", countryDTO);
        final CountryDTO savedCountryDTO = countryService.saveCountry(countryDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(savedCountryDTO.getCode())
                .toUri();

        LOGGER.debug("[CountryController] - Ending method [saveCountry] with return: {}", countryDTO);
        return ResponseEntity.created(location).body(savedCountryDTO);
    }

    public ResponseEntity<CountryDTO> updateCountry(@NotNull final Long id, final CountryDTO countryDTO) {
        LOGGER.debug("[CountryController] - Starting method [updateCountry] with input: id={}, countryDTO={}", id, countryDTO);
        final CountryDTO countryDTOSaved = countryService.updateCountry(id, countryDTO);
        LOGGER.debug("[CountryController] - Ending method [updateCountry] with return: {}", countryDTO);
        return ResponseEntity.ok().body(countryDTOSaved);
    }

    public ResponseEntity<?> deleteCountry(@NotNull final Long id) {
        LOGGER.debug("[CountryController] - Starting method [deleteCountry] with input: id={}", id);
        countryService.deleteCountry(id);
        LOGGER.debug("[CountryController] - Ending method [deleteCountry] with no return.");
        return ResponseEntity.noContent().build();
    }

}
