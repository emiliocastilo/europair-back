package com.europair.management.api.service.fleet;

import com.europair.management.api.dto.fleet.AircraftObservationDto;
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

@RequestMapping("/aircrafts/{aircraftId}/observations")
public interface IAircraftObservationController {


    /**
     * <p>
     * Retrieves aircraftObservation data identified by id.
     * </p>
     *
     * @param aircraftId Aircraft identifier
     * @param id         Unique identifier by id.
     * @return AircraftObservation data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master aircraftObservation data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftObservationDto> getAircraftObservationById(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "AircraftObservation identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of AircraftObservation filtered by properties criteria.
     * </p>
     *
     * @param aircraftId Aircraft identifier
     * @param pageable   pagination info
     * @param reqParam   Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of aircraftObservation
     */
    @GetMapping
    @Operation(description = "Paged result of master aircraftObservation with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<AircraftObservationDto>> getAircraftObservationByFilter(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new AircraftObservation master
     * </p>
     *
     * @param aircraftId             Aircraft identifier
     * @param aircraftObservationDto Data of the AircraftObservation to create
     * @return Data of the created aircraftObservation
     */
    @PostMapping
    @Operation(description = "Save a new master aircraftObservation", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftObservationDto> saveAircraftObservation(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "Master AircraftObservation object") @NotNull @RequestBody final AircraftObservationDto aircraftObservationDto);

    /**
     * <p>
     * Updated master aircraftObservation information
     * </p>
     *
     * @param aircraftId             Aircraft identifier
     * @param id                     Unique identifier
     * @param aircraftObservationDto Updated aircraftObservation data
     * @return The updated master aircraftObservation
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master aircraftObservation", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftObservationDto> updateAircraftObservation(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "AircraftObservation identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master AircraftObservation updated data") @NotNull @RequestBody final AircraftObservationDto aircraftObservationDto);

    /**
     * <p>
     * Deletes a master aircraftObservation by id.
     * </p>
     *
     * @param aircraftId Aircraft identifier
     * @param id         Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master aircraftObservation by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteAircraftObservation(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "AircraftObservation identifier") @PathVariable @NotNull final Long id);

}
