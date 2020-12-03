package com.europair.management.api.service.contract;

import com.europair.management.api.dto.contract.ContractConfigurationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;

@RequestMapping(value = {"/files/{fileId}/contracts/{contractId}/configuration",
        "/external/files/{fileId}/contracts/{contractId}/configuration"})
public interface IContractConfigurationController {


    /**
     * <p>
     * Retrieves contract configuration data identified by id.
     * </p>
     *
     * @param fileId     File identifier
     * @param contractId Contract identifier
     * @return Contract configuration data
     */
    @GetMapping
    @Operation(description = "Retrieve contract configuration data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContractConfigurationDto> getContractConfigurationById(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @NotNull @PathVariable final Long contractId
    );

    /**
     * <p>
     * Creates a new Contract configuration
     * </p>
     *
     * @param fileId                   File identifier
     * @param contractId               Contract identifier
     * @param contractConfigurationDto Data of the Contract configuration to create
     * @return Created
     */
    @PostMapping
    @Operation(description = "Save a new contract configuration", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> saveContractConfiguration(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @NotNull @PathVariable final Long contractId,
            @Parameter(description = "Contract configuration object") @NotNull @RequestBody final ContractConfigurationDto contractConfigurationDto);

    /**
     * <p>
     * Updates contract configuration
     * </p>
     *
     * @param fileId                   File identifier
     * @param contractId               Contract identifier
     * @param contractConfigurationDto Updated contract configuration data
     * @return No content
     */
    @PutMapping
    @Operation(description = "Updates contract configuration", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> updateContractConfiguration(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @NotNull @PathVariable final Long contractId,
            @Parameter(description = "Contract configuration updated data") @NotNull @RequestBody final ContractConfigurationDto contractConfigurationDto);

    /**
     * <p>
     * Deletes a contract configuration.
     * </p>
     *
     * @param fileId     File identifier
     * @param contractId Contract identifier
     * @return No content
     */
    @DeleteMapping
    @Operation(description = "Delete existing contract configuration by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteContractConfiguration(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Contract identifier") @NotNull @PathVariable final Long contractId);

}
