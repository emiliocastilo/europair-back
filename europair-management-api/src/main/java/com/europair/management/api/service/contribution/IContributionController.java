package com.europair.management.api.service.contribution;

import com.europair.management.api.dto.common.StateChangeDto;
import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.contribution.LineContributionRouteDTO;
import com.europair.management.api.enums.ContributionStatesEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

@RequestMapping(value = {"/files/{fileId}/routes/{routeId}/contributions", "/external/files/{fileId}/routes/{routeId}/contributions"})
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
     * @param routeId  Route identifier
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of contribution
     */
    @GetMapping
    @Operation(description = "Paged result of contributions with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ContributionDTO>> getContributionByFilter(
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);


    /**
     * <p>
     * Retrieves a paginated list of Contributions filtered by properties criteria.
     * </p>
     *
     * @param pageable       pagination info
     * @param contributionId
     * @param reqParam       Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of contribution
     */
    @GetMapping("/{contributionId}/linecontributionroute")
    @Operation(description = "Paged result of contributions with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<LineContributionRouteDTO>> getLineContributionRouteByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId,
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

    @PostMapping("/{contributionId}/linecontributionroute")
    @Operation(description = "Save a new LineContributionRoute", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Long> saveLineContributionRoute(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId,
            @Parameter(description = "LineContributionRoute object") @NotNull @RequestBody final LineContributionRouteDTO lineContributionRouteDTO);

    /**
     * <p>
     * Updated master contribution information
     * </p>
     *
     * @param id              Unique identifier
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
     * Update amount of a LineContributionRoute
     * </p>
     *
     * @param contributionId          Unique identifier
     * @param lineContributionRouteId Unique identifier
     * @return
     */
    @PutMapping("/{contributionId}/linecontributionroute/{lineContributionRouteId}")
    @Operation(description = "Updates existing linecontributionroute", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<HttpStatus> updateLineContributionRoute(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId,
            @Parameter(description = "LineContributionRoute identifier") @NotNull @PathVariable final Long lineContributionRouteId,
            @Parameter(description = "Amount to be updated") @RequestBody final LineContributionRouteDTO lineContributionRouteDTO);

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

    @DeleteMapping("/{contributionId}/linecontributionroute/{lineContributionRouteId}")
    @Operation(description = "Soft Delete existing master contribution by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteLineContributionRoute(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Contribution identifier") @PathVariable @NotNull final Long contributionId,
            @Parameter(description = "LineContributionRoute identifier") @PathVariable @NotNull final Long lineContributionRouteId);

    /**
     * <p>Changes a contribution state</p>
     *
     * @param fileId         File identifier
     * @param routeId        Route identifier
     * @param stateChangeDto State change data
     * @return No content
     */
    @PutMapping("/state")
    @Operation(description = "Changes the state of a contribution")
    ResponseEntity<?> changeState(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "State change data") @NotNull @RequestBody final StateChangeDto<ContributionStatesEnum> stateChangeDto);

    /**
     * <p>Gets a list of the states that the contribution can change</p>
     *
     * @param fileId  File identifier
     * @param routeId Route identifier
     * @param id      Contribution identifier
     * @return No content
     */
    @GetMapping("/{id}/state")
    @Operation(description = "Get valid state changes of a contribution")
    ResponseEntity<List<String>> getValidContributionStatesToChange(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Contribution identifier") @PathVariable @NotNull final Long id);

    /**
     * <p>
     * Creates route contribution sale lines with the values of the purchase lines
     * </p>
     *
     * @param contributionId Unique identifier of the contribution.
     * @return No content
     */
    @PostMapping("/{contributionId}/generate-sale-lines")
    @Operation(description = "Creates or updates route sale lines for the contribution", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> generateRouteContributionSaleLines(@Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId);

    /**
     * <p>
     * Retrieves a contribution line with the specified identifier
     * </p>
     *
     * @param fileId         File identifier
     * @param routeId        Route identifier
     * @param contributionId Contribution identifier
     * @param lineId         Contribution Line identifier
     * @return contribution line data
     */
    @GetMapping("/{contributionId}/linecontributionroute/{lineId}")
    @Operation(description = "Retrieve contribution line data by id", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<LineContributionRouteDTO> getLineContributionRouteById(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId,
            @Parameter(description = "Contribution line identifier") @NotNull @PathVariable final Long lineId);
}
