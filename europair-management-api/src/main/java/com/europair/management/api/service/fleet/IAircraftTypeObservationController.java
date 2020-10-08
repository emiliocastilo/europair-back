package com.europair.management.api.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeObservationDto;
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

@RequestMapping(value = {"/aircraft-types/{aircraftTypeId}/observations", "/external/aircraft-types/{aircraftTypeId}/observations"})
public interface IAircraftTypeObservationController {


    /**
     * <p>
     * Retrieves aircraftTypeObservation data identified by id.
     * </p>
     *
     * @param aircraftTypeId AircraftType identifier
     * @param id             Unique identifier by id.
     * @return AircraftTypeObservation data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master aircraftTypeObservation data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftTypeObservationDto> getAircraftTypeObservationById(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "AircraftTypeObservation identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of AircraftTypeObservation filtered by properties criteria.
     * </p>
     *
     * @param aircraftTypeId AircraftType identifier
     * @param pageable       pagination info
     * @param reqParam       Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of aircraftTypeObservation
     */
    @GetMapping
    @Operation(description = "Paged result of master aircraftTypeObservation with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<AircraftTypeObservationDto>> getAircraftTypeObservationByFilter(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new AircraftTypeObservation master
     * </p>
     *
     * @param aircraftTypeId             AircraftType identifier
     * @param aircraftTypeObservationDto Data of the AircraftTypeObservation to create
     * @return Data of the created aircraftTypeObservation
     */
    @PostMapping
    @Operation(description = "Save a new master aircraftTypeObservation", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftTypeObservationDto> saveAircraftTypeObservation(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "Master AircraftTypeObservation object") @NotNull @RequestBody final AircraftTypeObservationDto aircraftTypeObservationDto);

    /**
     * <p>
     * Updated master aircraftTypeObservation information
     * </p>
     *
     * @param aircraftTypeId             AircraftType identifier
     * @param id                         Unique identifier
     * @param aircraftTypeObservationDto Updated aircraftTypeObservation data
     * @return The updated master aircraftTypeObservation
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master aircraftTypeObservation", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftTypeObservationDto> updateAircraftTypeObservation(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "AircraftTypeObservation identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master AircraftTypeObservation updated data") @NotNull @RequestBody final AircraftTypeObservationDto aircraftTypeObservationDto);

    /**
     * <p>
     * Deletes a master aircraftTypeObservation by id.
     * </p>
     *
     * @param aircraftTypeId AircraftType identifier
     * @param id             Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master aircraftTypeObservation by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteAircraftTypeObservation(
            @Parameter(description = "AircraftType identifier") @NotNull @PathVariable final Long aircraftTypeId,
            @Parameter(description = "AircraftTypeObservation identifier") @PathVariable @NotNull final Long id);

}
