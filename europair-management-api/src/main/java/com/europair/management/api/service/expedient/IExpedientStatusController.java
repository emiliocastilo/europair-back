package com.europair.management.api.service.expedient;


import com.europair.management.api.dto.expedient.ExpedientStatusDto;
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

@RequestMapping("/expedientStatus")
public interface IExpedientStatusController {

    /**
     * <p>
     * Retrieves expedientStatus data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return ExpedientStatus data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master expedientStatus data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ExpedientStatusDto> getExpedientStatusById(@Parameter(description = "ExpedientStatus identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of ExpedientStatus filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of expedientStatus
     */
    @GetMapping
    @Operation(description = "Paged result of master expedientStatus with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ExpedientStatusDto>> getExpedientStatusByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new ExpedientStatus master
     * </p>
     *
     * @param expedientStatusDto Data of the ExpedientStatus to create
     * @return Data of the created expedientStatus
     */
    @PostMapping
    @Operation(description = "Save a new master expedientStatus", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ExpedientStatusDto> saveExpedientStatus(@Parameter(description = "Master ExpedientStatus object") @NotNull @RequestBody final ExpedientStatusDto expedientStatusDto);

    /**
     * <p>
     * Updated master expedientStatus information
     * </p>
     *
     * @param id                 Unique identifier
     * @param expedientStatusDto Updated expedientStatus data
     * @return The updated master expedientStatus
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master expedientStatus", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ExpedientStatusDto> updateExpedientStatus(
            @Parameter(description = "ExpedientStatus identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master ExpedientStatus updated data") @NotNull @RequestBody final ExpedientStatusDto expedientStatusDto);

    /**
     * <p>
     * Deletes a master expedientStatus by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing master expedientStatus by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteExpedientStatus(@Parameter(description = "ExpedientStatus identifier") @PathVariable @NotNull final Long id);

}
