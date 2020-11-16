package com.europair.management.impl.service.operators;


import com.europair.management.api.dto.operators.OperatorCommentDTO;
import com.europair.management.api.service.operators.IOperatorCommentController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OperatorCommentController implements IOperatorCommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorCommentController.class);

    private final IOperatorCommentService operatorCommentService;

    @Override
    public ResponseEntity<Page<OperatorCommentDTO>> getAllOperatorCommentsPaginated(
            final Pageable pageable,
            @PathVariable final Long operatorId) {
        LOGGER.debug("[OperatorCommentController] - Starting method [getAllOperatorCommentsPaginated] with input: pageable={}, operatorId={}",
                pageable, operatorId);
        final Page<OperatorCommentDTO> pageOperatorCommentDTO = operatorCommentService.findAllPaginated(pageable, operatorId);
        LOGGER.debug("[OperatorCommentController] - Ending method [getAllOperatorCommentsPaginated] with return: {}", pageOperatorCommentDTO);
        return ResponseEntity.ok().body(pageOperatorCommentDTO);
    }

    @Override
    public ResponseEntity<OperatorCommentDTO> getOperatorCommentById(
            @PathVariable final Long id,
            @PathVariable final Long operatorId) {
        LOGGER.debug("[OperatorCommentController] - Starting method [getOperatorCommentById] with input: id={}, operatorId={}", id, operatorId);
        final OperatorCommentDTO operatorCommentDTO = operatorCommentService.findById(id, operatorId);
        LOGGER.debug("[OperatorCommentController] - Ending method [getOperatorCommentById] with return: {}", operatorCommentDTO);
        return ResponseEntity.ok().body(operatorCommentDTO);
    }

    @Override
    public ResponseEntity<OperatorCommentDTO> saveOperatorComment(
            @RequestBody final OperatorCommentDTO operatorCommentDTO,
            @PathVariable final Long operatorId) {
        LOGGER.debug("[OperatorCommentController] - Starting method [saveOperatorComment] with input: operatorId={}, operatorCommentDTO={}", operatorId, operatorCommentDTO);
        final OperatorCommentDTO operatorCommentDTOSaved = operatorCommentService.saveOperatorComment(operatorCommentDTO, operatorId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(operatorCommentDTOSaved.getId())
                .toUri();
        LOGGER.debug("[OperatorCommentController] - Ending method [saveOperatorComment] with return: {}", operatorCommentDTOSaved);
        return ResponseEntity.created(location).body(operatorCommentDTOSaved);
    }

    @Override
    public ResponseEntity<OperatorCommentDTO> updateOperatorComment(
            @PathVariable final Long id,
            @RequestBody final OperatorCommentDTO operatorCommentDTO,
            @PathVariable final Long operatorId) {
        LOGGER.debug("[OperatorCommentController] - Starting method [updateOperatorComment] with input: id={}, operatorCommentDTO={}, operatorId={}",
                id, operatorCommentDTO, operatorId);
        final OperatorCommentDTO operatorCommentDTOUpdated = operatorCommentService.updateOperatorComment(id, operatorCommentDTO, operatorId);
        LOGGER.debug("[OperatorCommentController] - Ending method [updateOperatorComment] with return: {}", operatorCommentDTOUpdated);
        return ResponseEntity.ok().body(operatorCommentDTOUpdated);
    }

    @Override
    public ResponseEntity<?> deleteOperatorComment(
            @PathVariable final Long id,
            @PathVariable final Long operatorId) {
        LOGGER.debug("[OperatorCommentController] - Starting method [deleteOperatorComment] with input: id={}, operatorId={}",
                id, operatorId);
        operatorCommentService.deleteOperatorComment(id, operatorId);
        LOGGER.debug("[OperatorCommentController] - Ending method [deleteOperatorComment] with no return.");
        return ResponseEntity.noContent().build();
    }

}
