package com.europair.management.api.service.contract;

import com.europair.management.api.dto.contract.ContractCancelFeeDto;
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

@RequestMapping(value = {"/contract-cancel-fee", "/external/contract-cancel-fee"})
public interface IContractCancelFeeController {

    /**
     * <p>
     * Retrieves contractCancelFee data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return ContractCancelFee data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master contractCancelFee data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContractCancelFeeDto> getContractCancelFeeById(
            @Parameter(description = "ContractCancelFee identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of ContractCancelFee filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of contractCancelFee
     */
    @GetMapping
    @Operation(description = "Paged result of master contractCancelFee with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ContractCancelFeeDto>> getContractCancelFeeByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new ContractCancelFee master
     * </p>
     *
     * @param contractCancelFeeDto Data of the ContractCancelFee to create
     * @return Data of the created contractCancelFee
     */
    @PostMapping
    @Operation(description = "Save a new master contractCancelFee", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContractCancelFeeDto> saveContractCancelFee(
            @Parameter(description = "Master ContractCancelFee object") @NotNull @RequestBody final ContractCancelFeeDto contractCancelFeeDto);

    /**
     * <p>
     * Updated master contractCancelFee information
     * </p>
     *
     * @param id                   Unique identifier
     * @param contractCancelFeeDto Updated contractCancelFee data
     * @return The updated master contractCancelFee
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master contractCancelFee", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContractCancelFeeDto> updateContractCancelFee(
            @Parameter(description = "ContractCancelFee identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master ContractCancelFee updated data") @NotNull @RequestBody final ContractCancelFeeDto contractCancelFeeDto);

    /**
     * <p>
     * Deletes a master contractCancelFee by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master contractCancelFee by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteContractCancelFee(
            @Parameter(description = "ContractCancelFee identifier") @PathVariable @NotNull final Long id);

}
