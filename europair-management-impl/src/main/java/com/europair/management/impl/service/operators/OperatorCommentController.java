package com.europair.management.impl.service.operators;


import com.europair.management.api.dto.operators.OperatorCommentDTO;
import com.europair.management.api.service.operators.IOperatorCommentController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/operators/{operatorId}/comments")
public class OperatorCommentController implements IOperatorCommentController {

  private final IOperatorCommentService operatorCommentService;

  @GetMapping("")
  @Operation(description = "Paged result of operator comments list", security = { @SecurityRequirement(name = "bearerAuth") })
  @Override
  public ResponseEntity<Page<OperatorCommentDTO>> getAllOperatorCommentsPaginated(
      final Pageable pageable,
      @PathVariable final Long operatorId) {

    final Page<OperatorCommentDTO> pageOperatorCommentDTO = operatorCommentService.findAllPaginated(pageable, operatorId);
    return ResponseEntity.ok().body(pageOperatorCommentDTO);

  }

  @GetMapping("/{id}")
  @Operation(description = "Retrieve operator comment by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  @Override
  public ResponseEntity<OperatorCommentDTO> getOperatorCommentById(
      @PathVariable final Long id,
      @PathVariable final Long operatorId) {

    final OperatorCommentDTO operatorCommentDTO = operatorCommentService.findById(id, operatorId);
    return ResponseEntity.ok().body(operatorCommentDTO);
  }

  @PostMapping("")
  @Operation(description = "Save a new operator comment", security = { @SecurityRequirement(name = "bearerAuth") })
  @Override
  public ResponseEntity<OperatorCommentDTO> saveOperatorComment(
      @RequestBody final OperatorCommentDTO operatorCommentDTO,
      @PathVariable final Long operatorId) {

    final OperatorCommentDTO operatorCommentDTOSaved = operatorCommentService.saveOperatorComment(operatorCommentDTO, operatorId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(operatorCommentDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(operatorCommentDTOSaved);

  }

  @PutMapping("/{id}")
  @Operation(description = "Updates existing operator comment", security = { @SecurityRequirement(name = "bearerAuth") })
  @Override
  public ResponseEntity<OperatorCommentDTO> updateOperatorComment(
      @PathVariable final Long id,
      @RequestBody final OperatorCommentDTO operatorCommentDTO,
      @PathVariable final Long operatorId) {

    final OperatorCommentDTO operatorCommentDTOUpdated = operatorCommentService.updateOperatorComment(id, operatorCommentDTO, operatorId);

    return ResponseEntity.ok().body(operatorCommentDTOUpdated);

  }

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing operator comment by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  @Override
  public ResponseEntity<?> deleteOperatorComment(
      @PathVariable final Long id,
      @PathVariable final Long operatorId) {

    operatorCommentService.deleteOperatorComment(id, operatorId);
    return ResponseEntity.noContent().build();

  }

}
