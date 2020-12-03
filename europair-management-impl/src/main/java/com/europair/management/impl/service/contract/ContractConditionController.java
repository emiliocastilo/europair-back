package com.europair.management.impl.service.contract;


import com.europair.management.api.dto.contract.ContractConditionCopyDto;
import com.europair.management.api.dto.contract.ContractConditionDto;
import com.europair.management.api.service.contract.IContractConditionController;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class ContractConditionController implements IContractConditionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractConditionController.class);

    @Autowired
    private IContractConditionService contractConditionService;

    @Override
    public ResponseEntity<ContractConditionDto> getContractConditionById(@NotNull Long id) {
        LOGGER.debug("[ContractConditionController] - Starting method [getContractConditionById] with input: id={}", id);
        ContractConditionDto dto = contractConditionService.findById(id);
        LOGGER.debug("[ContractConditionController] - Ending method [getContractConditionById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<ContractConditionDto>> getContractConditionByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ContractConditionController] - Starting method [getContractConditionByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ContractConditionDto> dtoPage = contractConditionService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[ContractConditionController] - Ending method [getContractConditionByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<?> saveContractCondition(@NotNull ContractConditionDto contractConditionDto) {
        LOGGER.debug("[ContractConditionController] - Starting method [saveContractCondition] with input: contractConditionDto={}", contractConditionDto);
        final Long entityId = contractConditionService.saveContractCondition(contractConditionDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entityId)
                .toUri();

        LOGGER.debug("[ContractConditionController] - Ending method [saveContractCondition] with no return.");
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<?> updateContractCondition(@NotNull Long id, @NotNull ContractConditionDto contractConditionDto) {
        LOGGER.debug("[ContractConditionController] - Starting method [updateContractCondition] with input: contractConditionDto={}", contractConditionDto);
        contractConditionService.updateContractCondition(id, contractConditionDto);
        LOGGER.debug("[ContractConditionController] - Ending method [updateContractCondition] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteContractCondition(@NotNull Long id) {
        LOGGER.debug("[ContractConditionController] - Starting method [deleteContractCondition] with input: id={}", id);
        contractConditionService.deleteContractCondition(id);
        LOGGER.debug("[ContractConditionController] - Ending method [deleteContractCondition] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> copyContractConditions(@NotNull @Valid ContractConditionCopyDto contractConditionCopyDto) {
        LOGGER.debug("[ContractConditionController] - Starting method [copyContractConditions] with input: contractConditionCopyDto={}", contractConditionCopyDto);
        contractConditionService.copyContractConditions(contractConditionCopyDto);
        LOGGER.debug("[ContractConditionController] - Ending method [copyContractConditions] with no return.");
        return ResponseEntity.noContent().build();
    }
}
