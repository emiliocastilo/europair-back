package com.europair.management.api.service.cities;


import com.europair.management.api.dto.cities.CityDTO;
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

@RequestMapping("/cities")
public interface ICityController {

    /**
     * <p>
     * Retrieves a paginated list of cities.
     * </p>
     *
     * @param pageable - pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return paginated list of cities.
     */
    @GetMapping
    @Operation(description = "Paged result of master city with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<CityDTO>> getAllCitiesPaginated(
            final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Retrieves city data identified by id.
     * </p>
     *
     * @param id - Unique identifier of the city.
     * @return City data.
     */
    @GetMapping("/{id}")
    ResponseEntity<CityDTO> getCityById(@Parameter(description = "City identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Creates new city.
     * </p>
     *
     * @param cityDTO - City data
     * @return New city data.
     */
    @PostMapping
    @Operation(description = "Retrieve master city data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<CityDTO> saveCity(@Parameter(description = "Master City object") @NotNull @RequestBody final CityDTO cityDTO);

    /**
     * <p>
     * Updates city data identified by id.
     * </p>
     *
     * @param id      - Unique identifier of the city.
     * @param cityDTO - City data.
     * @return Updated city data.
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master city", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<CityDTO> updateCity(
            @Parameter(description = "City identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master City updated data") @NotNull @RequestBody final CityDTO cityDTO);

    /**
     * <p>
     * Removes city data identified by id.
     * </p>
     *
     * @param id - Unique identifier of the city.
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing master city by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteCity(@Parameter(description = "City identifier") @NotNull @PathVariable final Long id);

}
