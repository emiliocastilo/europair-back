package com.europair.management.api.service.contract;

import com.europair.management.api.dto.common.StateChangeDto;
import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.api.dto.contract.ContractLineDto;
import com.europair.management.api.enums.ContractStatesEnum;
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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RequestMapping(value = {"/files/{fileId}/contracts", "/external/files/{fileId}/contracts"})
public interface IContractController {


    /**
     * <p>
     * Retrieves contract data identified by id.
     * </p>
     *
     * @param fileId File identifier
     * @param id     Unique identifier by id.
     * @return Contract data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master contract data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContractDto> getContractById(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of Contract filtered by properties criteria.
     * </p>
     *
     * @param fileId   File identifier
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of contract
     */
    @GetMapping
    @Operation(description = "Paged result of master contract with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ContractDto>> getContractByFilter(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Contract master
     * </p>
     *
     * @param fileId      File identifier
     * @param contractDto Data of the Contract to create
     * @return Created
     */
    @PostMapping
    @Operation(description = "Save a new master contract", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> saveContract(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Master Contract object") @NotNull @RequestBody final ContractDto contractDto);

    /**
     * <p>
     * Updates master contract information
     * </p>
     *
     * @param fileId      File identifier
     * @param id          Unique identifier
     * @param contractDto Updated contract data
     * @return No content
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master contract", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> updateContract(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Contract updated data") @NotNull @RequestBody final ContractDto contractDto);

    /**
     * <p>
     * Deletes a master contract by id.
     * </p>
     *
     * @param fileId File identifier
     * @param id     Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master contract by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteContract(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @PathVariable @NotNull final Long id);

    /**
     * <p>
     * Generates contracts for selected route
     * </p>
     *
     * @param fileId   File identifier
     * @param routeIds Route identifier
     * @return No data
     */
    @PostMapping("/generate")
    @Operation(description = "Generates contracts for the selected route", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> generateContracts(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "List of Route identifiers") @NotEmpty @RequestBody final List<Long> routeIds);

    /**
     * <p>Changes contract state</p>
     *
     * @param fileId         File identifier
     * @param stateChangeDto State change data
     * @return No content
     */
    @PutMapping("/state")
    @Operation(description = "Changes the state of contracts")
    ResponseEntity<?> changeState(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "State change data") @NotNull @RequestBody final StateChangeDto<ContractStatesEnum> stateChangeDto);

    /**
     * <p>
     * Copies all the contract data into a new contract
     * </p>
     *
     * @param fileId     File identifier
     * @param contractId Identifier of the contract to copy
     * @return No data
     */
    @PostMapping("/{contractId}/copy")
    @Operation(description = "Copies the selected contract into a new one", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> copyContract(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @NotNull @PathVariable final Long contractId);

    /**
     * <p>
     * Updates a contract line if the contract is not signed
     * </p>
     *
     * @param fileId          File identifier
     * @param contractId      Contract identifier
     * @param contractLineId  Contract line identifier
     * @param contractLineDto Data of the Contract line to update
     * @return No content
     */
    @PutMapping("/{contractId}/lines/{contractLineId}")
    @Operation(description = "Updates a contract line", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> updateContractLine(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @NotNull @PathVariable final Long contractId,
            @Parameter(description = "Contract line identifier") @NotNull @PathVariable final Long contractLineId,
            @Parameter(description = "Updated contract line object") @NotNull @RequestBody final ContractLineDto contractLineDto);

    /**
     * <p>
     * Deletes (soft) a contract line if the contract is not signed
     * </p>
     *
     * @param fileId         File identifier
     * @param contractId     Contract identifier
     * @param contractLineId Contract line identifier
     * @return No content
     */
    @DeleteMapping("/{contractId}/lines/{contractLineId}")
    @Operation(description = "Delete (soft) a contract line", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteContractLine(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @NotNull @PathVariable final Long contractId,
            @Parameter(description = "Contract line identifier") @NotNull @PathVariable final Long contractLineId);
}
