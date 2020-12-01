package com.europair.management.impl.service.contract;


import com.europair.management.api.dto.contract.ContractPaymentConditionDto;
import com.europair.management.api.service.contract.IContractPaymentConditionController;
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
public class ContractPaymentConditionController implements IContractPaymentConditionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractPaymentConditionController.class);

    @Autowired
    private IContractPaymentConditionService contractPaymentConditionService;

    @Override
    public ResponseEntity<ContractPaymentConditionDto> getContractPaymentConditionById(@NotNull Long id) {
        LOGGER.debug("[ContractPaymentConditionController] - Starting method [getContractPaymentConditionById] with input: id={}", id);
        ContractPaymentConditionDto dto = contractPaymentConditionService.findById(id);
        LOGGER.debug("[ContractPaymentConditionController] - Ending method [getContractPaymentConditionById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<ContractPaymentConditionDto>> getContractPaymentConditionByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ContractPaymentConditionController] - Starting method [getContractPaymentConditionByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ContractPaymentConditionDto> dtoPage = contractPaymentConditionService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[ContractPaymentConditionController] - Ending method [getContractPaymentConditionByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<ContractPaymentConditionDto> saveContractPaymentCondition(@NotNull ContractPaymentConditionDto contractPaymentConditionDto) {
        LOGGER.debug("[ContractPaymentConditionController] - Starting method [saveContractPaymentCondition] with input: contractPaymentConditionDto={}", contractPaymentConditionDto);
        final ContractPaymentConditionDto dtoSaved = contractPaymentConditionService.saveContractPaymentCondition(contractPaymentConditionDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        LOGGER.debug("[ContractPaymentConditionController] - Ending method [saveContractPaymentCondition] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<ContractPaymentConditionDto> updateContractPaymentCondition(@NotNull Long id, @NotNull ContractPaymentConditionDto contractPaymentConditionDto) {
        LOGGER.debug("[ContractPaymentConditionController] - Starting method [updateContractPaymentCondition] with input: contractPaymentConditionDto={}", contractPaymentConditionDto);
        final ContractPaymentConditionDto dtoSaved = contractPaymentConditionService.updateContractPaymentCondition(id, contractPaymentConditionDto);
        LOGGER.debug("[ContractPaymentConditionController] - Ending method [updateContractPaymentCondition] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteContractPaymentCondition(@NotNull Long id) {
        LOGGER.debug("[ContractPaymentConditionController] - Starting method [deleteContractPaymentCondition] with input: id={}", id);
        contractPaymentConditionService.deleteContractPaymentCondition(id);
        LOGGER.debug("[ContractPaymentConditionController] - Ending method [deleteContractPaymentCondition] with no return.");
        return ResponseEntity.noContent().build();
    }

}
