package com.europair.management.api.service.airport;

import com.europair.management.api.dto.airport.AirportDto;
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

@RequestMapping("/airports")
public interface IAirportController {


    /**
     * <p>
     * Retrieves airport data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return Airport data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master airport data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AirportDto> getAirportById(@Parameter(description = "Airport identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of Airport filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of airport
     */
    @GetMapping
    @Operation(description = "Paged result of master airport with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<AirportDto>> getAirportByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Airport master
     * </p>
     *
     * @param airportDto Data of the Airport to create
     * @return Data of the created airport
     */
    @PostMapping
    @Operation(description = "Save a new master airport", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AirportDto> saveAirport(@Parameter(description = "Master Airport object") @NotNull @RequestBody final AirportDto airportDto);

    /**
     * <p>
     * Updated master airport information
     * </p>
     *
     * @param id         Unique identifier
     * @param airportDto Updated airport data
     * @return The updated master airport
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master airport", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AirportDto> updateAirport(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Airport updated data") @NotNull @RequestBody final AirportDto airportDto);

    /**
     * <p>
     * Soft deletes a master airport by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Soft Delete existing master airport by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteAirport(@Parameter(description = "Airport identifier") @PathVariable @NotNull final Long id);

}
