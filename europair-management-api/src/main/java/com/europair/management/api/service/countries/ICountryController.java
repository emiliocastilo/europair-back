package com.europair.management.api.service.countries;

import com.europair.management.api.dto.countries.CountryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping("/countries")
public interface ICountryController {

    /**
     * <p>
     * Retrieves a paginated list of countries.
     * </p>
     *
     * @param pageable - pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return paginated list of countries.
     */
    @GetMapping
    @Operation(description = "Paged result of master country with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<CountryDTO>> getAllCountriesPaginated(
            final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Retrieves country data identified by id.
     * </p>
     *
     * @param id - Unique identifier of the country.
     * @return Country data.
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master country data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<CountryDTO> getCountryById(@Parameter(description = "Country identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Creates new country.
     * </p>
     *
     * @param countryDTO
     * @return New country data.
     */
    @PostMapping
    @Operation(description = "Save a new master country", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<CountryDTO> saveCountry(@Parameter(description = "Master Country object") @NotNull @RequestBody final CountryDTO countryDTO);

    /**
     * <p>
     * Updates country data identified by id.
     * </p>
     *
     * @param id         - Unique identifier of the country.
     * @param countryDTO - Country data.
     * @return Updated country data.
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master country", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<CountryDTO> updateCountry(
            @Parameter(description = "Country identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Country updated data") @NotNull @RequestBody final CountryDTO countryDTO);

    /**
     * <p>
     * Removes country data identified by id.
     * </p>
     *
     * @param id - Unique identifier of the country.
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing master country by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteCountry(@Parameter(description = "Country identifier") @NotNull @PathVariable final Long id);

}
