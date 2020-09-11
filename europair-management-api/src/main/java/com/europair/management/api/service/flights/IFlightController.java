package com.europair.management.api.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequestMapping("/files/{fileId}/routes/{routeId}/flights")
public interface IFlightController {

    /**
     * <p>
     * Retrieves a paginated list of Flight filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @return Paginated list of flights
     */
    @GetMapping
    @Operation(description = "Paged result of flights with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<FlightDTO>> getAllFlightsPaginated(
      @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
      @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
      @Parameter(description = "Pagination filter") final Pageable pageable);


    /**
     * <p>
     * Retrieves flight data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return Flight data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve flight data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FlightDTO> getFlightById(
      @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
      @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
      @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Creates a new Flight
     * </p>
     *
     * @param flightDTO Data of the Flight to create
     * @return Data of the created flight
     */
    @PostMapping
    @Operation(description = "Save a new flight", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FlightDTO> saveFlight(
      @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
      @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
      @Parameter(description = "Flight object") @NotNull @RequestBody final FlightDTO flightDTO);

    /**
     * <p>
     * Updates flight information
     * </p>
     *
     * @param id        Unique identifier
     * @param flightDTO Updated flight data
     * @return The updated flight
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing flight", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FlightDTO> updateFlight(
      @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
      @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
      @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long id,
      @Parameter(description = "Flight updated data") @NotNull @RequestBody final FlightDTO flightDTO);

    /**
     * <p>
     * Deletes a flight by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing flight by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteFlight(
      @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
      @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
      @Parameter(description = "Flight identifier") @PathVariable @NotNull final Long id);

}
