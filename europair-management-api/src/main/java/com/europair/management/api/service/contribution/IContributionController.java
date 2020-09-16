package com.europair.management.api.service.contribution;

import com.europair.management.api.dto.contribution.ContributionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping("/files/{fileId}/routes/{routeId}/contributions")
public interface IContributionController {

    /**
     * <p>
     * Retrieves Contribution data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return Contribution data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve contribution data by id contribution", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContributionDTO> getContributionById(@Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of Contributions filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of contribution
     */
    @GetMapping
    @Operation(description = "Paged result of contributions with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ContributionDTO>> getContributionByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Contribution master
     * </p>
     *
     * @param contributionDTO Data of the Contribution to create
     * @return Data of the created contribution
     */
    @PostMapping
    @Operation(description = "Save a new Contribution", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContributionDTO> saveContribution(@Parameter(description = "Contribution object") @NotNull @RequestBody final ContributionDTO contributionDTO);

    /**
     * <p>
     * Updated master contribution information
     * </p>
     *
     * @param id         Unique identifier
     * @param contributionDTO Updated contribution data
     * @return The updated contribution
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing contribution", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContributionDTO> updateContribution(
            @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Contribution updated data") @NotNull @RequestBody final ContributionDTO contributionDTO);

    /**
     * <p>
     * Soft deletes a contribution by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Soft Delete existing master contribution by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteContribution(@Parameter(description = "Contribution identifier") @PathVariable @NotNull final Long id);
}
