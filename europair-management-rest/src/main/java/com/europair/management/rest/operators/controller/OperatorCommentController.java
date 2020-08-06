package com.europair.management.rest.operators.controller;

import com.europair.management.rest.model.operators.dto.OperatorCommentDTO;
import com.europair.management.rest.operators.service.OperatorCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OperatorCommentController {

  private final OperatorCommentService operatorCommentService;

  @GetMapping("/operator/{operatorId}/comments")
  public ResponseEntity<Page<OperatorCommentDTO>> getAllOperatorCommentsPaginated(final Pageable pageable, @PathVariable final Long operatorId) {

    final Page<OperatorCommentDTO> pageOperatorCommentDTO = operatorCommentService.findAllPaginated(pageable, operatorId);
    return ResponseEntity.ok().body(pageOperatorCommentDTO);

  }

  @GetMapping("/operator/{operatorId}/comments/{id}")
  public ResponseEntity<OperatorCommentDTO> getOperatorCommentById(@PathVariable final Long id, @PathVariable final Long operatorId) {

    final OperatorCommentDTO operatorCommentDTO = operatorCommentService.findById(id, operatorId);
    return ResponseEntity.ok().body(operatorCommentDTO);
  }

  @PostMapping("/operator/{operatorId}/comments")
  public ResponseEntity<OperatorCommentDTO> saveOperatorComment(@RequestBody final OperatorCommentDTO operatorCommentDTO, @PathVariable final Long operatorId) {

    final OperatorCommentDTO operatorCommentDTOSaved = operatorCommentService.saveOperatorComment(operatorCommentDTO, operatorId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(operatorCommentDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(operatorCommentDTOSaved);

  }

  @PutMapping("/operator/{operatorId}/comments/{id}")
  public ResponseEntity<OperatorCommentDTO> updateOperator(@PathVariable final Long id, @RequestBody final OperatorCommentDTO operatorCommentDTO, @PathVariable final Long operatorId) {

    final OperatorCommentDTO operatorCommentDTOSaved = operatorCommentService.updateOperatorComment(id, operatorCommentDTO, operatorId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(operatorCommentDTOSaved.getId())
      .toUri();

    return ResponseEntity.ok().body(operatorCommentDTOSaved);

  }

  @DeleteMapping("/operator/{operatorId}/comments/{id}")
  public ResponseEntity<?> deleteCertification(@PathVariable final Long id, @PathVariable final Long operatorId) {

    operatorCommentService.deleteOperatorComment(id, operatorId);
    return ResponseEntity.noContent().build();

  }

}
