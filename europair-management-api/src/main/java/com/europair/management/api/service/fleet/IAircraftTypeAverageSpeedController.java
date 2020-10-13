package com.europair.management.api.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeAverageSpeedDto;
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

@RequestMapping(value = {"/aircraft-types/{aircraftTypeId}/average-speed", "/external/aircraft-types/{aircraftTypeId}/average-speed"})
public interface IAircraftTypeAverageSpeedController {


    /**
     * <p>
     * Retrieves aircraftTypeAverageSpeed data identified by id.
     * </p>
     *
     * @param aircraftTypeId AircraftType identifier
     * @param id             Unique identifier by id.
     * @return AircraftTypeAverageSpeed data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master aircraftTypeAverageSpeed data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftTypeAverageSpeedDto> getAircraftTypeAverageSpeedById(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "AircraftTypeAverageSpeed identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of AircraftTypeAverageSpeed filtered by properties criteria.
     * </p>
     *
     * @param aircraftTypeId AircraftType identifier
     * @param pageable       pagination info
     * @param reqParam       Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of aircraftTypeAverageSpeed
     */
    @GetMapping
    @Operation(description = "Paged result of master aircraftTypeAverageSpeed with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<AircraftTypeAverageSpeedDto>> getAircraftTypeAverageSpeedByFilter(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new AircraftTypeAverageSpeed master
     * </p>
     *
     * @param aircraftTypeId              AircraftType identifier
     * @param aircraftTypeAverageSpeedDto Data of the AircraftTypeAverageSpeed to create
     * @return Data of the created aircraftTypeAverageSpeed
     */
    @PostMapping
    @Operation(description = "Save a new master aircraftTypeAverageSpeed", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftTypeAverageSpeedDto> saveAircraftTypeAverageSpeed(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "Master AircraftTypeAverageSpeed object") @NotNull @RequestBody final AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto);

    /**
     * <p>
     * Updated master aircraftTypeAverageSpeed information
     * </p>
     *
     * @param aircraftTypeId              AircraftType identifier
     * @param id                          Unique identifier
     * @param aircraftTypeAverageSpeedDto Updated aircraftTypeAverageSpeed data
     * @return The updated master aircraftTypeAverageSpeed
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master aircraftTypeAverageSpeed", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftTypeAverageSpeedDto> updateAircraftTypeAverageSpeed(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "AircraftTypeAverageSpeed identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master AircraftTypeAverageSpeed updated data") @NotNull @RequestBody final AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto);

    /**
     * <p>
     * Deletes a master aircraftTypeAverageSpeed by id.
     * </p>
     *
     * @param aircraftTypeId AircraftType identifier
     * @param id             Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master aircraftTypeAverageSpeed by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteAircraftTypeAverageSpeed(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "AircraftTypeAverageSpeed identifier") @PathVariable @NotNull final Long id);

}
