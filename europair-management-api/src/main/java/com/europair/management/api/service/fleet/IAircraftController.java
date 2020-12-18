package com.europair.management.api.service.fleet;


import com.europair.management.api.dto.fleet.AircraftDto;
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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@RequestMapping(value = {"/aircrafts", "/external/aircrafts"})
public interface IAircraftController {

    /**
     * <p>
     * Retrieves aircraft data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return Aircraft data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master aircraft data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftDto> getAircraftById(@Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of Aircraft filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of aircraft
     */
    @GetMapping
    @Operation(description = "Paged result of master aircraft with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<AircraftDto>> getAircraftByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Aircraft master
     * </p>
     *
     * @param aircraftDto Data of the Aircraft to create
     * @return Data of the created aircraft
     */
    @PostMapping
    @Operation(description = "Save a new master aircraft", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftDto> saveAircraft(@Parameter(description = "Master Aircraft object") @NotNull @RequestBody final AircraftDto aircraftDto);

    /**
     * <p>
     * Updated master aircraft information
     * </p>
     *
     * @param id          Unique identifier
     * @param aircraftDto Updated aircraft data
     * @return The updated master aircraft
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master aircraft", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftDto> updateAircraft(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Aircraft updated data") @NotNull @RequestBody final AircraftDto aircraftDto);

    /**
     * <p>
     * Deletes a master aircraft by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing master aircraft by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteAircraft(@Parameter(description = "Aircraft identifier") @PathVariable @NotNull final Long id);

    /**
     * <p>
     * Reactivates the soft removed selected entities
     * </p>
     *
     * @param aircraftIds Aircraft id list
     * @return No content
     */
    @PutMapping("/reactivate")
    @Operation(description = "Reactivates soft deleted aircraft", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> reactivateAircraft(
            @Parameter(description = "List of Aircraft identifiers") @NotEmpty @RequestBody final Set<Long> aircraftIds);
}
