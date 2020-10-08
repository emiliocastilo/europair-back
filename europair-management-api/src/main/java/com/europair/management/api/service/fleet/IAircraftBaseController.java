package com.europair.management.api.service.fleet;

import com.europair.management.api.dto.fleet.AircraftBaseDto;
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

@RequestMapping(value = {"/aircrafts/{aircraftId}/bases", "/external/aircrafts/{aircraftId}/bases"})
public interface IAircraftBaseController {


    /**
     * <p>
     * Retrieves aircraftBase data identified by id.
     * </p>
     *
     * @param aircraftId Aircraft identifier
     * @param id         Unique identifier by id.
     * @return AircraftBase data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master aircraftBase data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftBaseDto> getAircraftBaseById(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "AircraftBase identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of AircraftBase filtered by properties criteria.
     * </p>
     *
     * @param aircraftId Aircraft identifier
     * @param pageable   pagination info
     * @param reqParam   Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of aircraftBase
     */
    @GetMapping
    @Operation(description = "Paged result of master aircraftBase with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<AircraftBaseDto>> getAircraftBaseByFilter(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new AircraftBase master
     * </p>
     *
     * @param aircraftId      Aircraft identifier
     * @param aircraftBaseDto Data of the AircraftBase to create
     * @return Data of the created aircraftBase
     */
    @PostMapping
    @Operation(description = "Save a new master aircraftBase", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftBaseDto> saveAircraftBase(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "Master AircraftBase object") @NotNull @RequestBody final AircraftBaseDto aircraftBaseDto);

    /**
     * <p>
     * Updated master aircraftBase information
     * </p>
     *
     * @param aircraftId      Aircraft identifier
     * @param id              Unique identifier
     * @param aircraftBaseDto Updated aircraftBase data
     * @return The updated master aircraftBase
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master aircraftBase", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<AircraftBaseDto> updateAircraftBase(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "AircraftBase identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master AircraftBase updated data") @NotNull @RequestBody final AircraftBaseDto aircraftBaseDto);

    /**
     * <p>
     * Deletes a master aircraftBase by id.
     * </p>
     *
     * @param aircraftId Aircraft identifier
     * @param id         Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master aircraftBase by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteAircraftBase(
            @Parameter(description = "Aircraft identifier") @NotNull @PathVariable final Long aircraftId,
            @Parameter(description = "AircraftBase identifier") @PathVariable @NotNull final Long id);

}
