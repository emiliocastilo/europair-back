package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractConfigurationDto;
import com.europair.management.api.service.contract.IContractConfigurationController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@Slf4j
public class ContractConfigurationController implements IContractConfigurationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractConfigurationController.class);

    @Autowired
    private IContractConfigurationService contractConfigurationService;

    @Override
    public ResponseEntity<ContractConfigurationDto> getContractConfigurationById(@NotNull Long fileId, @NotNull Long contractId) {
        LOGGER.debug("[ContractConfigurationController] - Starting method [getContractConfigurationById] with input: fileId={}, contractId={}", fileId, contractId);
        ContractConfigurationDto dto = contractConfigurationService.findById(fileId, contractId);
        LOGGER.debug("[ContractConfigurationController] - Ending method [getContractConfigurationById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> saveContractConfiguration(@NotNull Long fileId, @NotNull Long contractId, @NotNull ContractConfigurationDto contractConfigurationDto) {
        LOGGER.debug("[ContractConfigurationController] - Starting method [saveContractConfiguration] with input: fileId={}, contractId={}, contractConfigurationDto={}",
                fileId, contractId, contractConfigurationDto);
        final Long entityId = contractConfigurationService.saveContractConfiguration(fileId, contractId, contractConfigurationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entityId)
                .toUri();

        LOGGER.debug("[ContractConfigurationController] - Ending method [saveContractConfiguration] with no return.");
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<?> updateContractConfiguration(@NotNull Long fileId, @NotNull Long contractId, @NotNull ContractConfigurationDto contractConfigurationDto) {
        LOGGER.debug("[ContractConfigurationController] - Starting method [updateContractConfiguration] with input: fileId={}, contractId={}, contractConfigurationDto={}",
                fileId, contractId, contractConfigurationDto);
        contractConfigurationService.updateContractConfiguration(fileId, contractId, contractConfigurationDto);
        LOGGER.debug("[ContractConfigurationController] - Ending method [updateContractConfiguration] with no return");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteContractConfiguration(@NotNull Long fileId, @NotNull Long contractId) {
        LOGGER.debug("[ContractConfigurationController] - Starting method [deleteContractConfiguration] with input: fileId={}, contractId={}",
                fileId, contractId);
        contractConfigurationService.deleteContractConfiguration(fileId, contractId);
        LOGGER.debug("[ContractConfigurationController] - Ending method [deleteContractConfiguration] with no return.");
        return ResponseEntity.noContent().build();
    }
}
