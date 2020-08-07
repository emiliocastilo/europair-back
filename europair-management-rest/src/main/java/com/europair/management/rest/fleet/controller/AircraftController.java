package com.europair.management.rest.fleet.controller;

import com.europair.management.rest.common.utils.Utils;
import com.europair.management.rest.fleet.service.AircraftService;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.dto.AircraftDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/aircrafts")
public class AircraftController {

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

}
