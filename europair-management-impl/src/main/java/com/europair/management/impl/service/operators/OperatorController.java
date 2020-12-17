package com.europair.management.impl.service.operators;


import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.service.operators.IOperatorController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OperatorController implements IOperatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorController.class);

    private final IOperatorService operatorService;

    @Override
    public ResponseEntity<Page<OperatorDTO>> getOperatorsByFilter(final Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[OperatorController] - Starting method [getOperatorsByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<OperatorDTO> pageOperatorsDTO = operatorService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[OperatorController] - Ending method [getOperatorsByFilter] with return: {}", pageOperatorsDTO);
        return ResponseEntity.ok().body(pageOperatorsDTO);
    }

    @Override
    public ResponseEntity<OperatorDTO> getOperatorById(@PathVariable final Long id) {
        LOGGER.debug("[OperatorController] - Starting method [getOperatorById] with input: id={}", id);
        final OperatorDTO operatorDTO = operatorService.findById(id);
        LOGGER.debug("[OperatorController] - Ending method [getOperatorById] with return: {}", operatorDTO);
        return ResponseEntity.ok().body(operatorDTO);
    }

    @Override
    public ResponseEntity<Page<OperatorDTO>> getOperatorsByFilter(@RequestParam final String text, Pageable pageable) {
        LOGGER.debug("[OperatorController] - Starting method [getOperatorsByFilter] with input: text={}, pageable={}", text, pageable);
        final Page<OperatorDTO> pageOperatorsDTO = operatorService.searchOperator(text, pageable);
        LOGGER.debug("[OperatorController] - Ending method [getOperatorsByFilter] with return: {}", pageOperatorsDTO);
        return ResponseEntity.ok().body(pageOperatorsDTO);
    }

    @Override
    public ResponseEntity<OperatorDTO> saveOperator(@RequestBody final OperatorDTO operatorDTO) {
        LOGGER.debug("[OperatorController] - Starting method [saveOperator] with input: operatorDTO={}", operatorDTO);
        final OperatorDTO operatorDTOSaved = operatorService.saveOperator(operatorDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(operatorDTOSaved.getId())
                .toUri();
        LOGGER.debug("[OperatorController] - Ending method [saveOperator] with return: {}", operatorDTOSaved);
        return ResponseEntity.created(location).body(operatorDTOSaved);
    }

    @Override
    public ResponseEntity<OperatorDTO> updateOperator(@NotNull final Long id, @NotNull final OperatorDTO operatorDTO) {
        LOGGER.debug("[OperatorController] - Starting method [updateOperator] with input: id={}, operatorDTO={}", id, operatorDTO);
        final OperatorDTO operatorDTOUpdated = operatorService.updateOperator(id, operatorDTO);
        LOGGER.debug("[OperatorController] - Ending method [updateOperator] with return: {}", operatorDTOUpdated);
        return ResponseEntity.ok().body(operatorDTOUpdated);
    }

    public ResponseEntity<?> deleteOperator(@NotNull final Long id) {
        LOGGER.debug("[OperatorController] - Starting method [deleteOperator] with input: id={}", id);
        operatorService.deleteOperator(id);
        LOGGER.debug("[OperatorController] - Ending method [deleteOperator] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> reactivateOperators(@NotEmpty Set<Long> operatorIds) {
        LOGGER.debug("[OperatorController] - Starting method [reactivateOperators] with input: operatorIds={}", operatorIds);
        operatorService.reactivateOperators(operatorIds);
        LOGGER.debug("[OperatorController] - Ending method [reactivateOperators] with no return.");
        return ResponseEntity.noContent().build();
    }
}
