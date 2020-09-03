package com.europair.management.impl.service.countries;


import com.europair.management.api.dto.countries.CountryDTO;
import com.europair.management.api.service.countries.ICountryController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ICountryService countryService;

    public ResponseEntity<Page<CountryDTO>> getAllCountriesPaginated(final Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<CountryDTO> countryDTOPage = countryService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok().body(countryDTOPage);
    }

    public ResponseEntity<CountryDTO> getCountryById(@NotNull final Long id) {
        final CountryDTO countryDTO = countryService.findById(id);
        return ResponseEntity.ok().body(countryDTO);
    }

    public ResponseEntity<CountryDTO> saveCountry(final CountryDTO countryDTO) {
        final CountryDTO savedCountryDTO = countryService.saveCountry(countryDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(savedCountryDTO.getCode())
                .toUri();

        return ResponseEntity.created(location).body(savedCountryDTO);
    }

    public ResponseEntity<CountryDTO> updateCountry(@NotNull final Long id, final CountryDTO countryDTO) {
        final CountryDTO countryDTOSaved = countryService.updateCountry(id, countryDTO);
        return ResponseEntity.ok().body(countryDTOSaved);
    }

    public ResponseEntity<?> deleteCountry(@NotNull final Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }

}
