package com.europair.management.api.service.airport;

import com.europair.management.api.dto.airport.AirportObservationDto;
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

@RequestMapping(value = {"/airports/{airportId}/observations", "/external/airports/{airportId}/observations"})
public interface IAirportObservationController {


    /**
     * <p>
     * Retrieves airportObservation data identified by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier by id.
     * @return AirportObservation data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master airportObservation data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AirportObservationDto> getAirportObservationById(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "AirportObservation identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of AirportObservation filtered by properties criteria.
     * </p>
     *
     * @param airportId Airport identifier
     * @param pageable  pagination info
     * @param reqParam  Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of airportObservation
     */
    @GetMapping
    @Operation(description = "Paged result of master airportObservation with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<AirportObservationDto>> getAirportObservationByFilter(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new AirportObservation master
     * </p>
     *
     * @param airportId             Airport identifier
     * @param airportObservationDto Data of the AirportObservation to create
     * @return Data of the created airportObservation
     */
    @PostMapping
    @Operation(description = "Save a new master airportObservation", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AirportObservationDto> saveAirportObservation(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Master AirportObservation object") @NotNull @RequestBody final AirportObservationDto airportObservationDto);

    /**
     * <p>
     * Updated master airportObservation information
     * </p>
     *
     * @param airportId             Airport identifier
     * @param id                    Unique identifier
     * @param airportObservationDto Updated airportObservation data
     * @return The updated master airportObservation
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master airportObservation", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AirportObservationDto> updateAirportObservation(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "AirportObservation identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master AirportObservation updated data") @NotNull @RequestBody final AirportObservationDto airportObservationDto);

    /**
     * <p>
     * Deletes a master airportObservation by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master airportObservation by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteAirportObservation(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "AirportObservation identifier") @PathVariable @NotNull final Long id);

}
