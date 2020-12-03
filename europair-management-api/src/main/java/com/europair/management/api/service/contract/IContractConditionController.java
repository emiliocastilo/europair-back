package com.europair.management.api.service.contract;

import com.europair.management.api.dto.contract.ContractConditionCopyDto;
import com.europair.management.api.dto.contract.ContractConditionDto;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping(value = {"/contract-conditions", "/external/contract-conditions"})
public interface IContractConditionController {

    /**
     * <p>
     * Retrieves contractCondition data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return ContractCondition data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master contractCondition data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContractConditionDto> getContractConditionById(
            @Parameter(description = "ContractCondition identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of ContractCondition filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of contractCondition
     */
    @GetMapping
    @Operation(description = "Paged result of master contractCondition with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ContractConditionDto>> getContractConditionByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new ContractCondition master
     * </p>
     *
     * @param contractConditionDto Data of the ContractCondition to create
     * @return Created
     */
    @PostMapping
    @Operation(description = "Save a new master contractCondition", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> saveContractCondition(
            @Parameter(description = "Master ContractCondition object") @NotNull @RequestBody final ContractConditionDto contractConditionDto);

    /**
     * <p>
     * Updated master contractCondition information
     * </p>
     *
     * @param id                   Unique identifier
     * @param contractConditionDto Updated contractCondition data
     * @return No content
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master contractCondition", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> updateContractCondition(
            @Parameter(description = "ContractCondition identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master ContractCondition updated data") @NotNull @RequestBody final ContractConditionDto contractConditionDto);

    /**
     * <p>
     * Deletes a master contractCondition by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master contractCondition by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteContractCondition(
            @Parameter(description = "ContractCondition identifier") @PathVariable @NotNull final Long id);


    /**
     * <p>
     * Creates new ContractCondition as a copy from existent ones
     * </p>
     *
     * @param contractConditionCopyDto Data with the contract Id and the condition ids to copy
     * @return No content
     */
    @PostMapping
    @Operation(description = "Copy contractConditions to a contract from existent ones", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> copyContractConditions(
            @Parameter(description = "ContractConditionCopyDto object") @NotNull @Valid @RequestBody final ContractConditionCopyDto contractConditionCopyDto);
}
