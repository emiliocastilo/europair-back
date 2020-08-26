package com.europair.management.api.service.airport;

import com.europair.management.api.dto.regions.RegionDTO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;

@RequestMapping("/airports/{airportId}/regions")
public interface IAirportRegionController {


    /**
     * <p>
     * Retrieves region data identified by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier by id.
     * @return Region data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master region data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<RegionDTO> getRegionById(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Region identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of Region.
     * </p>
     *
     * @param airportId Airport identifier
     * @param pageable  pagination info
     * @return Paginated list of region
     */
    @GetMapping
    @Operation(description = "Paged result of master region", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<RegionDTO>> getRegionByFilter(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Pagination filter") final Pageable pageable);

    /**
     * <p>
     * Creates a new Region master
     * </p>
     *
     * @param airportId Airport identifier
     * @param regionDto Data of the Region to create
     * @return Data of the created region
     */
    @PostMapping
    @Operation(description = "Save a new master region", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<RegionDTO> saveRegion(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Master Region object") @NotNull @RequestBody final RegionDTO regionDto);

    /**
     * <p>
     * Deletes a master region by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master region by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteRegion(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Region identifier") @PathVariable @NotNull final Long id);

}
