package com.europair.management.api.service.operators;


import com.europair.management.api.dto.operators.OperatorCommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface IOperatorCommentController {


  @GetMapping("/operator/{operatorId}/comments")
  @Operation(description = "Paged result of operator comments list", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<OperatorCommentDTO>> getAllOperatorCommentsPaginated(final Pageable pageable, @PathVariable final Long operatorId) ;

  @GetMapping("/operator/{operatorId}/comments/{id}")
  @Operation(description = "Retrieve operator comment by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorCommentDTO> getOperatorCommentById(@PathVariable final Long id, @PathVariable final Long operatorId) ;

  @PostMapping("/operator/{operatorId}/comments")
  @Operation(description = "Save a new operator comment", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorCommentDTO> saveOperatorComment(@RequestBody final OperatorCommentDTO operatorCommentDTO, @PathVariable final Long operatorId) ;

  @PutMapping("/operator/{operatorId}/comments/{id}")
  @Operation(description = "Updates existing operator comment", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorCommentDTO> updateOperator(@PathVariable final Long id, @RequestBody final OperatorCommentDTO operatorCommentDTO, @PathVariable final Long operatorId) ;

  @DeleteMapping("/operator/{operatorId}/comments/{id}")
  @Operation(description = "Deletes existing operator comment by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteCertification(@PathVariable final Long id, @PathVariable final Long operatorId) ;

}
