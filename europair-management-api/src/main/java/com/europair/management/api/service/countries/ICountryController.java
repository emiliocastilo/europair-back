package com.europair.management.api.service.countries;

import com.europair.management.api.dto.countries.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/countries")
public interface ICountryController {

    /**
     * <p>
     *     Retrieves a paginated list of countries.
     * </p>
     * @param pageable - pagination info
     * @return paginated list of countries.
     */

    @GetMapping("")
    public ResponseEntity<Page<CountryDTO>> getAllCountriesPaginated(final Pageable pageable) ;

    /**
     * <p>
     *     Retrieves country data identified by id.
     * </p>
     * @param id - Unique identifier of the country.
     * @return Country data.
     */

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable final Long id) ;

    /**
     * <p>
     *     Creates new country.
     * </p>
     * @param countryDTO
     * @return New country data.
     */

    @PostMapping("")
    public ResponseEntity<CountryDTO> saveCountry(@RequestBody final CountryDTO countryDTO) ;

    /**
     * <p>
     *     Updates country data identified by id.
     * </p>
     * @param id - Unique identifier of the country.
     * @param countryDTO - Country data.
     * @return Updated country data.
     */

    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable final Long id, @RequestBody final CountryDTO countryDTO) ;

    /**
     * <p>
     *     Removes country data identified by id.
     * </p>
     * @param id - Unique identifier of the country.
     * @return
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable final Long id) ;

}
