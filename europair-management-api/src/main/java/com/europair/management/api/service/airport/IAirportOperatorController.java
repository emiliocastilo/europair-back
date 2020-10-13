package com.europair.management.api.service.airport;

import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
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

@RequestMapping(value = {"/airports/{airportId}/operators", "/external/airports/{airportId}/operators"})
public interface IAirportOperatorController {


    /**
     * <p>
     * Retrieves operatorsAirports data identified by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier by id.
     * @return OperatorsAirports data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master operatorsAirports data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<OperatorsAirportsDTO> getOperatorsAirportsById(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "OperatorsAirports identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of OperatorsAirports filtered by properties criteria.
     * </p>
     *
     * @param airportId Airport identifier
     * @param pageable  pagination info
     * @param reqParam  Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of operatorsAirports
     */
    @GetMapping
    @Operation(description = "Paged result of master operatorsAirports with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<OperatorsAirportsDTO>> getOperatorsAirportsByFilter(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new OperatorsAirports master
     * </p>
     *
     * @param airportId            Airport identifier
     * @param operatorsAirportsDto Data of the OperatorsAirports to create
     * @return Data of the created operatorsAirports
     */
    @PostMapping
    @Operation(description = "Save a new master operatorsAirports", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<OperatorsAirportsDTO> saveOperatorsAirports(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Master OperatorsAirports object") @NotNull @RequestBody final OperatorsAirportsDTO operatorsAirportsDto);

    /**
     * <p>
     * Updated master operatorsAirports information
     * </p>
     *
     * @param airportId            Airport identifier
     * @param id                   Unique identifier
     * @param operatorsAirportsDto Updated operatorsAirports data
     * @return The updated master operatorsAirports
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master operatorsAirports", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<OperatorsAirportsDTO> updateOperatorsAirports(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "OperatorsAirports identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master OperatorsAirports updated data") @NotNull @RequestBody final OperatorsAirportsDTO operatorsAirportsDto);

    /**
     * <p>
     * Deletes a master operatorsAirports by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master operatorsAirports by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteOperatorsAirports(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "OperatorsAirports identifier") @PathVariable @NotNull final Long id);

}
