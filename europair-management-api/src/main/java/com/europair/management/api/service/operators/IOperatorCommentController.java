package com.europair.management.api.service.operators;


import com.europair.management.api.dto.operators.OperatorCommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/operators/{operatorId}/comments")
public interface IOperatorCommentController {

  /**
   * <p>
   * Retrieves a paginated list of Operator comments.
   * </p>
   *
   * @param pageable pagination info
   * @return Paginated list of operator comments
   */
  @GetMapping("")
  @Operation(description = "Paged result of operator comments list", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<Page<OperatorCommentDTO>> getAllOperatorCommentsPaginated(
      final Pageable pageable,
      @PathVariable final Long operatorId) ;

  /**
   * <p>
   * Retrieves operator comment by id.
   * </p>
   *
   * @param id Unique identifier by id.
   * @return Operator comment
   */
  @GetMapping("/{id}")
  @Operation(description = "Retrieve operator comment by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<OperatorCommentDTO> getOperatorCommentById(
      @PathVariable final Long id, @PathVariable final Long operatorId) ;

  /**
   * <p>
   * Creates a new Operator comment
   * </p>
   *
   * @param operatorCommentDTO Info of the Operator to create
   * @return Data of the created operator comment
   */
  @PostMapping("")
  @Operation(description = "Save a new operator comment", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<OperatorCommentDTO> saveOperatorComment(
      @RequestBody final OperatorCommentDTO operatorCommentDTO,
      @PathVariable final Long operatorId) ;

  /**
   * <p>
   * Updates operator comment
   * </p>
   *
   * @param id         Unique identifier
   * @param operatorCommentDTO Updated operator comment
   * @return The updated opertor comment
   */
  @PutMapping("/{id}")
  @Operation(description = "Updates existing operator comment", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<OperatorCommentDTO> updateOperatorComment(
      @PathVariable final Long id,
      @RequestBody final OperatorCommentDTO operatorCommentDTO,
      @PathVariable final Long operatorId) ;

  /**
   * <p>
   * Deletes an operator comment by id.
   * </p>
   *
   * @param id Unique identifier
   * @return No content
   */
  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing operator comment by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<?> deleteOperatorComment(
      @PathVariable final Long id,
      @PathVariable final Long operatorId) ;

}
