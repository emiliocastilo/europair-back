package com.europair.management.api.service.contract;

import com.europair.management.api.dto.contract.ContractPaymentConditionDto;
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

@RequestMapping(value = {"/contract-payment-conditions", "/external/contract-payment-conditions"})
public interface IContractPaymentConditionController {

    /**
     * <p>
     * Retrieves contractPaymentCondition data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return ContractPaymentCondition data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master contractPaymentCondition data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContractPaymentConditionDto> getContractPaymentConditionById(
            @Parameter(description = "ContractPaymentCondition identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of ContractPaymentCondition filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of contractPaymentCondition
     */
    @GetMapping
    @Operation(description = "Paged result of master contractPaymentCondition with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ContractPaymentConditionDto>> getContractPaymentConditionByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new ContractPaymentCondition master
     * </p>
     *
     * @param contractPaymentConditionDto Data of the ContractPaymentCondition to create
     * @return No data
     */
    @PostMapping
    @Operation(description = "Save a new master contractPaymentCondition", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> saveContractPaymentCondition(
            @Parameter(description = "Master ContractPaymentCondition object") @NotNull @RequestBody final ContractPaymentConditionDto contractPaymentConditionDto);

    /**
     * <p>
     * Updated master contractPaymentCondition information
     * </p>
     *
     * @param id                          Unique identifier
     * @param contractPaymentConditionDto Updated contractPaymentCondition data
     * @return No content
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master contractPaymentCondition", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> updateContractPaymentCondition(
            @Parameter(description = "ContractPaymentCondition identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master ContractPaymentCondition updated data") @NotNull @RequestBody final ContractPaymentConditionDto contractPaymentConditionDto);

    /**
     * <p>
     * Deletes a master contractPaymentCondition by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master contractPaymentCondition by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteContractPaymentCondition(
            @Parameter(description = "ContractPaymentCondition identifier") @PathVariable @NotNull final Long id);

}
