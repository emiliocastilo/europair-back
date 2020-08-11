package com.europair.management.impl.service.operators.controller;


import com.europair.management.api.dto.operators.dto.OperatorCommentDTO;
import com.europair.management.impl.service.operators.service.IOperatorCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

  private final IOperatorCommentService operatorCommentService;

  @GetMapping("/operator/{operatorId}/comments")
  @Operation(description = "Paged result of operator comments list", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<OperatorCommentDTO>> getAllOperatorCommentsPaginated(final Pageable pageable, @PathVariable final Long operatorId) {

    final Page<OperatorCommentDTO> pageOperatorCommentDTO = operatorCommentService.findAllPaginated(pageable, operatorId);
    return ResponseEntity.ok().body(pageOperatorCommentDTO);

  }

  @GetMapping("/operator/{operatorId}/comments/{id}")
  @Operation(description = "Retrieve operator comment by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorCommentDTO> getOperatorCommentById(@PathVariable final Long id, @PathVariable final Long operatorId) {

    final OperatorCommentDTO operatorCommentDTO = operatorCommentService.findById(id, operatorId);
    return ResponseEntity.ok().body(operatorCommentDTO);
  }

  @PostMapping("/operator/{operatorId}/comments")
  @Operation(description = "Save a new operator comment", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorCommentDTO> saveOperatorComment(@RequestBody final OperatorCommentDTO operatorCommentDTO, @PathVariable final Long operatorId) {

    final OperatorCommentDTO operatorCommentDTOSaved = operatorCommentService.saveOperatorComment(operatorCommentDTO, operatorId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(operatorCommentDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(operatorCommentDTOSaved);

  }

  @PutMapping("/operator/{operatorId}/comments/{id}")
  @Operation(description = "Updates existing operator comment", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorCommentDTO> updateOperator(@PathVariable final Long id, @RequestBody final OperatorCommentDTO operatorCommentDTO, @PathVariable final Long operatorId) {

    final OperatorCommentDTO operatorCommentDTOSaved = operatorCommentService.updateOperatorComment(id, operatorCommentDTO, operatorId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(operatorCommentDTOSaved.getId())
      .toUri();

    return ResponseEntity.ok().body(operatorCommentDTOSaved);

  }

  @DeleteMapping("/operator/{operatorId}/comments/{id}")
  @Operation(description = "Deletes existing operator comment by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteCertification(@PathVariable final Long id, @PathVariable final Long operatorId) {

    operatorCommentService.deleteOperatorComment(id, operatorId);
    return ResponseEntity.noContent().build();

  }

}
