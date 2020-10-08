package com.europair.management.api.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeDto;
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

@RequestMapping(value = {"/aircraft-types","/external/aircraft-types"})
public interface IAircraftTypeController {

    /**
     * <p>
     * Retrieves aircraftType data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return AircraftType data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master aircraftType data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftTypeDto> getAircraftTypeById(@Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of AircraftType filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of aircraftType
     */
    @GetMapping
    @Operation(description = "Paged result of master aircraftType with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<AircraftTypeDto>> getAircraftTypeByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new AircraftType master
     * </p>
     *
     * @param aircraftTypeDto Data of the AircraftType to create
     * @return Data of the created aircraftType
     */
    @PostMapping
    @Operation(description = "Save a new master aircraftType", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftTypeDto> saveAircraftType(@Parameter(description = "Master AircraftType object") @NotNull @RequestBody final AircraftTypeDto aircraftTypeDto);

    /**
     * <p>
     * Updated master aircraftType information
     * </p>
     *
     * @param id              Unique identifier
     * @param aircraftTypeDto Updated aircraftType data
     * @return The updated master aircraftType
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master aircraftType", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftTypeDto> updateAircraftType(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master AircraftType updated data") @NotNull @RequestBody final AircraftTypeDto aircraftTypeDto);

    /**
     * <p>
     * Soft deletes a master aircraftType by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Soft Delete existing master aircraftType by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteAircraftType(@Parameter(description = "AircraftType identifier") @PathVariable @NotNull final Long id);

}
