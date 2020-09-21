package com.europair.management.api.service.flights;

import com.europair.management.api.dto.flights.FlightServiceDto;
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

@RequestMapping("/files/{fileId}/routes/{routeId}/flights/{flightId}/services")
public interface IFlightServiceController {

    /**
     * <p>
     * Retrieves a paginated list of Flight Services filtered by properties criteria.
     * </p>
     *
     * @param fileId   File identifier
     * @param routeId  Route (rotation) identifier
     * @param flightId Flight identifier
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: filter_description=asd,CONTAINS)
     * @return Paginated list of flights
     */
    @GetMapping
    @Operation(description = "Paged result of flight services with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<FlightServiceDto>> getAllFlightServicesPaginated(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long flightId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: filter_description=asd,CONTAINS)") @RequestParam
                    Map<String, String> reqParam);


    /**
     * <p>
     * Retrieves flight service data identified by id.
     * </p>
     *
     * @param fileId   File identifier
     * @param routeId  Route (rotation) identifier
     * @param flightId Flight identifier
     * @param id       Unique identifier by id.
     * @return Flight service data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve flight service data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FlightServiceDto> getFlightServiceById(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long flightId,
            @Parameter(description = "Flight service identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Creates a new Flight Service
     * </p>
     *
     * @param fileId           File identifier
     * @param routeId          Route (rotation) identifier
     * @param flightId         Flight identifier
     * @param flightServiceDto Data of the Flight service to create
     * @return Data of the created flight service
     */
    @PostMapping
    @Operation(description = "Save a new flight service", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FlightServiceDto> saveFlightService(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long flightId,
            @Parameter(description = "Flight service data") @NotNull @RequestBody final FlightServiceDto flightServiceDto);

    /**
     * <p>
     * Updates flight service information
     * </p>
     *
     * @param fileId           File identifier
     * @param routeId          Route (rotation) identifier
     * @param flightId         Flight identifier
     * @param id               Unique identifier
     * @param flightServiceDto Updated flight service data
     * @return The updated flight
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing flight service", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FlightServiceDto> updateFlightService(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long flightId,
            @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Flight updated data") @NotNull @RequestBody final FlightServiceDto flightServiceDto);

    /**
     * <p>
     * Deletes a flight service by id.
     * </p>
     *
     * @param fileId   File identifier
     * @param routeId  Route (rotation) identifier
     * @param flightId Flight identifier
     * @param id       Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing flight service by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteFlightService(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long flightId,
            @Parameter(description = "Flight identifier") @PathVariable @NotNull final Long id);

}
