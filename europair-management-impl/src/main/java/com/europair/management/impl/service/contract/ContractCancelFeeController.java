package com.europair.management.impl.service.contract;


import com.europair.management.api.dto.contract.ContractCancelFeeDto;
import com.europair.management.api.service.contract.IContractCancelFeeController;
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
public class ContractCancelFeeController implements IContractCancelFeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCancelFeeController.class);

    @Autowired
    private IContractCancelFeeService contractCancelFeeService;

    @Override
    public ResponseEntity<ContractCancelFeeDto> getContractCancelFeeById(@NotNull Long id) {
        LOGGER.debug("[ContractCancelFeeController] - Starting method [getContractCancelFeeById] with input: id={}", id);
        ContractCancelFeeDto dto = contractCancelFeeService.findById(id);
        LOGGER.debug("[ContractCancelFeeController] - Ending method [getContractCancelFeeById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<ContractCancelFeeDto>> getContractCancelFeeByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ContractCancelFeeController] - Starting method [getContractCancelFeeByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ContractCancelFeeDto> dtoPage = contractCancelFeeService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[ContractCancelFeeController] - Ending method [getContractCancelFeeByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<?> saveContractCancelFee(@NotNull ContractCancelFeeDto contractCancelFeeDto) {
        LOGGER.debug("[ContractCancelFeeController] - Starting method [saveContractCancelFee] with input: contractCancelFeeDto={}", contractCancelFeeDto);
        final Long entityId = contractCancelFeeService.saveContractCancelFee(contractCancelFeeDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entityId)
                .toUri();

        LOGGER.debug("[ContractCancelFeeController] - Ending method [saveContractCancelFee] with no return");
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<?> updateContractCancelFee(@NotNull Long id, @NotNull ContractCancelFeeDto contractCancelFeeDto) {
        LOGGER.debug("[ContractCancelFeeController] - Starting method [updateContractCancelFee] with input: contractCancelFeeDto={}", contractCancelFeeDto);
        contractCancelFeeService.updateContractCancelFee(id, contractCancelFeeDto);
        LOGGER.debug("[ContractCancelFeeController] - Ending method [updateContractCancelFee] with no return");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteContractCancelFee(@NotNull Long id) {
        LOGGER.debug("[ContractCancelFeeController] - Starting method [deleteContractCancelFee] with input: id={}", id);
        contractCancelFeeService.deleteContractCancelFee(id);
        LOGGER.debug("[ContractCancelFeeController] - Ending method [deleteContractCancelFee] with no return.");
        return ResponseEntity.noContent().build();
    }

}
