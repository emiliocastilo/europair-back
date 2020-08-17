package com.europair.management.api.service.airport;

import com.europair.management.api.dto.airport.RunwayDto;
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

@RequestMapping("/airports/{airportId}/runways")
public interface IRunwayController {


    /**
     * <p>
     * Retrieves runway data identified by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier by id.
     * @return Runway data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master runway data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<RunwayDto> getRunwayById(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Runway identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of Runway filtered by properties criteria.
     * </p>
     *
     * @param airportId Airport identifier
     * @param pageable  pagination info
     * @param reqParam  Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of runway
     */
    @GetMapping
    @Operation(description = "Paged result of master runway with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<RunwayDto>> getRunwayByFilter(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Runway master
     * </p>
     *
     * @param airportId Airport identifier
     * @param runwayDto Data of the Runway to create
     * @return Data of the created runway
     */
    @PostMapping
    @Operation(description = "Save a new master runway", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<RunwayDto> saveRunway(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Master Runway object") @NotNull @RequestBody final RunwayDto runwayDto);

    /**
     * <p>
     * Updated master runway information
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier
     * @param runwayDto Updated runway data
     * @return The updated master runway
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master runway", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<RunwayDto> updateRunway(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Runway identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Runway updated data") @NotNull @RequestBody final RunwayDto runwayDto);

    /**
     * <p>
     * Soft deletes a master runway by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Soft Delete existing master runway by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteRunway(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Runway identifier") @PathVariable @NotNull final Long id);

}
