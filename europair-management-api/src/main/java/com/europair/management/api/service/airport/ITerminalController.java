package com.europair.management.api.service.airport;

import com.europair.management.api.dto.airport.TerminalDto;
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

@RequestMapping(value = {"/airports/{airportId}/terminals", "/external/airports/{airportId}/terminals"})
public interface ITerminalController {


    /**
     * <p>
     * Retrieves terminal data identified by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier by id.
     * @return Terminal data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master terminal data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<TerminalDto> getTerminalById(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Terminal identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of Terminal filtered by properties criteria.
     * </p>
     *
     * @param airportId Airport identifier
     * @param pageable  pagination info
     * @param reqParam  Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of terminal
     */
    @GetMapping
    @Operation(description = "Paged result of master terminal with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<TerminalDto>> getTerminalByFilter(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Terminal master
     * </p>
     *
     * @param airportId   Airport identifier
     * @param terminalDto Data of the Terminal to create
     * @return Data of the created terminal
     */
    @PostMapping
    @Operation(description = "Save a new master terminal", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<TerminalDto> saveTerminal(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Master Terminal object") @NotNull @RequestBody final TerminalDto terminalDto);

    /**
     * <p>
     * Updated master terminal information
     * </p>
     *
     * @param airportId   Airport identifier
     * @param id          Unique identifier
     * @param terminalDto Updated terminal data
     * @return The updated master terminal
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master terminal", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<TerminalDto> updateTerminal(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Terminal identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Terminal updated data") @NotNull @RequestBody final TerminalDto terminalDto);

    /**
     * <p>
     * Deletes a master terminal by id.
     * </p>
     *
     * @param airportId Airport identifier
     * @param id        Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master terminal by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteTerminal(
            @Parameter(description = "Airport identifier") @NotNull @PathVariable final Long airportId,
            @Parameter(description = "Terminal identifier") @PathVariable @NotNull final Long id);

}
