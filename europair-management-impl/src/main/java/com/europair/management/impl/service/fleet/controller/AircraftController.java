package com.europair.management.impl.service.fleet.controller;

import com.europair.management.api.dto.fleet.dto.AircraftDto;
import com.europair.management.api.service.fleet.IAircraftController;
import com.europair.management.impl.service.fleet.service.AircraftService;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/aircrafts")
public class AircraftController implements IAircraftController {

    @Autowired
    private AircraftService aircraftService;

    /**
     * <p>
     * Retrieves a paginated list of Aircraft.
     * </p>
     *
     * @param pageable pagination info
     * @return Paginated list of aircraft
     */
    @GetMapping
    @Operation(description = "Paged result of master aircraft", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<AircraftDto>> getAllAircraftPaginated(@Parameter(description = "Pagination filter") final Pageable pageable) {
        final Page<AircraftDto> aircraftDtoPage = aircraftService.findAllPaginated(pageable);
        return ResponseEntity.ok(aircraftDtoPage);
    }

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
    public ResponseEntity<AircraftDto> getAircraftById(@Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long id) {
        final AircraftDto aircraftDto = aircraftService.findById(id);
        return ResponseEntity.ok(aircraftDto);
    }

    /**
     * <p>
     * Retrieves a paginated list of Aircraft filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of aircraft
     */
    @GetMapping("/filter")
    @Operation(description = "Paged result of master aircraft with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<AircraftDto>> getAircraftByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftDto> aircraftDtoPage = aircraftService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(aircraftDtoPage);
    }

    /**
     * <p>
     * Retrieves a paginated list of Aircraft filtered by matching the value of some properties:
     * operator, aircraftType, bases.airport
     * </p>
     *
     * @param pageable pagination info
     * @param filter   User input filter value
     * @return Paginated list of aircraft
     */
    @GetMapping("/filter/basic")
    @Operation(description = "Paged result of master aircraft with basic filter", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<AircraftDto>> getAircraftsByBasicFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Filter value") @RequestParam final String filter) {
        final Page<AircraftDto> aircraftDtoPage = aircraftService.findAllPaginatedByBasicFilter(pageable, filter);
        return ResponseEntity.ok(aircraftDtoPage);
    }

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
    public ResponseEntity<AircraftDto> saveAircraft(@Parameter(description = "Master Aircraft object") @NotNull @RequestBody final AircraftDto aircraftDto) {

        final AircraftDto aircraftDtoSaved = aircraftService.saveAircraft(aircraftDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftDtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(aircraftDtoSaved);

    }

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
    public ResponseEntity<AircraftDto> updateAircraft(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Aircraft updated data") @NotNull @RequestBody final AircraftDto aircraftDto) {

        final AircraftDto aircraftDtoSaved = aircraftService.updateAircraft(id, aircraftDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftDtoSaved.getId())
                .toUri();

        return ResponseEntity.ok().body(aircraftDtoSaved);

    }

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
    public ResponseEntity<?> deleteAircraft(@Parameter(description = "Aircraft identifier") @PathVariable @NotNull final Long id) {

        aircraftService.deleteAircraft(id);
        return ResponseEntity.noContent().build();

    }

}
