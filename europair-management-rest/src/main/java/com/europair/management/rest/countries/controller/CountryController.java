package com.europair.management.rest.countries.controller;

import com.europair.management.rest.countries.service.CountryService;
import com.europair.management.rest.model.countries.dto.CountryDTO;
import com.europair.management.rest.model.roles.dto.RoleDTO;
import com.europair.management.rest.roles.service.RoleService;
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
public class CountryController {

    private final CountryService countryService;

    @GetMapping("")
    public ResponseEntity<Page<CountryDTO>> getAllCountriesPaginated(final Pageable pageable) {

        final Page<CountryDTO> countryDTOPage = countryService.findAllPaginated(pageable);
        return ResponseEntity.ok().body(countryDTOPage);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable final Long id) {

        final CountryDTO countryDTO = countryService.findById(id);
        return ResponseEntity.ok().body(countryDTO);

    }

    @PostMapping("")
    public ResponseEntity<CountryDTO> saveCountry(@RequestBody final CountryDTO countryDTO) {

        final CountryDTO savedCountryDTO = countryService.saveCountry(countryDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(savedCountryDTO.getCode())
                .toUri();

        return ResponseEntity.created(location).body(savedCountryDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable final Long id, @RequestBody final CountryDTO countryDTO) {

        final CountryDTO countryDTOSaved = countryService.updateCountry(id, countryDTO);

        return ResponseEntity.ok().body(countryDTOSaved);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable final Long id) {

        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();

    }

}
