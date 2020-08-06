package com.europair.management.impl.service.countries.controller;


import com.europair.management.api.dto.countries.dto.CountryDTO;
import com.europair.management.api.service.countries.controller.ICountryController;
import com.europair.management.impl.service.countries.service.ICountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/countries")
public class CountryController implements ICountryController {

    private final ICountryService countryService;

    /**
     * <p>
     *     Retrieves a paginated list of countries.
     * </p>
     * @param pageable - pagination info
     * @return paginated list of countries.
     */

    @GetMapping("")
    public ResponseEntity<Page<CountryDTO>> getAllCountriesPaginated(final Pageable pageable) {

        final Page<CountryDTO> countryDTOPage = countryService.findAllPaginated(pageable);
        return ResponseEntity.ok().body(countryDTOPage);

    }

    /**
     * <p>
     *     Retrieves country data identified by id.
     * </p>
     * @param id - Unique identifier of the country.
     * @return Country data.
     */

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable final Long id) {

        final CountryDTO countryDTO = countryService.findById(id);
        return ResponseEntity.ok().body(countryDTO);

    }

    /**
     * <p>
     *     Creates new country.
     * </p>
     * @param countryDTO
     * @return New country data.
     */

    @PostMapping("")
    public ResponseEntity<CountryDTO> saveCountry(@RequestBody final CountryDTO countryDTO) {

        final CountryDTO savedCountryDTO = countryService.saveCountry(countryDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(savedCountryDTO.getCode())
                .toUri();

        return ResponseEntity.created(location).body(savedCountryDTO);

    }

    /**
     * <p>
     *     Updates country data identified by id.
     * </p>
     * @param id - Unique identifier of the country.
     * @param countryDTO - Country data.
     * @return Updated country data.
     */

    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable final Long id, @RequestBody final CountryDTO countryDTO) {

        final CountryDTO countryDTOSaved = countryService.updateCountry(id, countryDTO);

        return ResponseEntity.ok().body(countryDTOSaved);

    }

    /**
     * <p>
     *     Removes country data identified by id.
     * </p>
     * @param id - Unique identifier of the country.
     * @return
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable final Long id) {

        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();

    }

}
