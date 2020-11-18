package com.europair.management.impl.service.contract;


import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.api.service.contract.IContractController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class ContractController implements IContractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private IContractService contractService;

    @Override
    public ResponseEntity<ContractDto> getContractById(@NotNull Long fileId, @NotNull Long id) {
        LOGGER.debug("[ContractController] - Starting method [getContractById] with input: fileId={}, id={}", fileId, id);
        ContractDto dto = contractService.findById(fileId, id);
        LOGGER.debug("[ContractController] - Ending method [getContractById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<ContractDto>> getContractByFilter(@NotNull Long fileId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ContractController] - Starting method [getContractByFilter] with input: fileId={}, pageable={}, reqParam={}", fileId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ContractDto> dtoPage = contractService.findAllPaginatedByFilter(fileId, pageable, criteria);
        LOGGER.debug("[ContractController] - Ending method [getContractByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<ContractDto> saveContract(@NotNull Long fileId, @NotNull ContractDto contractDto) {
        LOGGER.debug("[ContractController] - Starting method [saveContract] with input: fileId={}, contractDto={}", fileId, contractDto);
        final ContractDto dtoSaved = contractService.saveContract(fileId, contractDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileId, dtoSaved.getId())
                .toUri();

        LOGGER.debug("[ContractController] - Ending method [saveContract] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<ContractDto> updateContract(@NotNull Long fileId, @NotNull Long id, @NotNull ContractDto contractDto) {
        LOGGER.debug("[ContractController] - Starting method [updateContract] with input: fileId={}, contractDto={}", fileId, contractDto);
        final ContractDto dtoSaved = contractService.updateContract(fileId, id, contractDto);
        LOGGER.debug("[ContractController] - Ending method [updateContract] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteContract(@NotNull Long fileId, @NotNull Long id) {
        LOGGER.debug("[ContractController] - Starting method [deleteContract] with input: fileId={}, id={}", fileId, id);
        contractService.deleteContract(fileId, id);
        LOGGER.debug("[ContractController] - Ending method [deleteContract] with no return.");
        return ResponseEntity.noContent().build();
    }
}
