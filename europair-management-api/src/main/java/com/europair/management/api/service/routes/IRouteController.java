package com.europair.management.api.service.routes;

import com.europair.management.api.dto.common.StateChangeDto;
import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.routes.RouteCreationDto;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.RouteStatesEnum;
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
import java.util.List;
import java.util.Map;

@RequestMapping(value = {"/files/{fileId}/routes", "/external/files/{fileId}/routes"})
public interface IRouteController {


    /**
     * <p>
     * Retrieves route data identified by id.
     * </p>
     *
     * @param fileId File identifier
     * @param id     Unique identifier by id.
     * @return Route data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master route data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<RouteDto> getRouteById(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of Route filtered by properties criteria.
     * </p>
     *
     * @param fileId   File identifier
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of route
     */
    @GetMapping
    @Operation(description = "Paged result of master route with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<RouteDto>> getRouteByFilter(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Route master
     * </p>
     *
     * @param fileId   File identifier
     * @param routeDto Data of the Route to create
     * @return Data of the created route
     */
    @PostMapping
    @Operation(description = "Save a new master route", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<RouteDto> saveRoute(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Master Route object") @NotNull @RequestBody final RouteCreationDto routeDto);

    /**
     * <p>
     * Updated master route information
     * </p>
     *
     * @param fileId   File identifier
     * @param id       Unique identifier
     * @param routeDto Updated route data
     * @return The updated master route
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master route", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<RouteDto> updateRoute(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Route updated data") @NotNull @RequestBody final RouteDto routeDto);

    /**
     * <p>
     * Deletes a master route by id.
     * </p>
     *
     * @param fileId File identifier
     * @param id     Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master route by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteRoute(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @PathVariable @NotNull final Long id);

    /**
     * <p>
     * Update rotation route information
     * </p>
     *
     * @param fileId   File identifier
     * @param routeId  Unique parent route identifier
     * @param id       Unique identifier
     * @param routeDto Updated route data
     * @return The updated rotation route
     */
    @PutMapping("/{routeId}/rotations/{id}")
    @Operation(description = "Updates existing rotation route", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<RouteDto> updateRotation(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Parent route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Route updated data") @NotNull @RequestBody final RouteDto routeDto);

    /**
     * <p>Changes a route state</p>
     *
     * @param fileId         File identifier
     * @param stateChangeDto State change data
     * @return No content
     */
    @PutMapping("/state")
    @Operation(description = "Changes the state of a route")
    ResponseEntity<?> changeState(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "State change data") @NotNull @RequestBody final StateChangeDto<RouteStatesEnum> stateChangeDto);

    @GetMapping("/{routeId}/withcontribution")
    ResponseEntity<List<ContributionDTO>> getRouteWithContributions(@Parameter(description = "Parent route identifier") @NotNull @PathVariable final Long routeId);
}
