package com.europair.management.api.service.operators;


import com.europair.management.api.dto.operators.OperatorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping("/operators")
public interface IOperatorController {


  /**
   * <p>
   * Retrieves a paginated list of Operator filtered by properties criteria.
   * </p>
   *
   * @param pageable pagination info
   * @param reqParam Map of filter params, values and operators. (pe: iataCode=UX,CONTAINS)
   * @return Paginated list of airport
   */
  @GetMapping("")
  @Operation(description = "Paged result of operators list", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<Page<OperatorDTO>> getOperatorsByFilter(
      @Parameter(description = "Pagination filter") final Pageable pageable,
      @Parameter(description = "Map of properties to filter with value and operator, (pe: iataCode=UX,CONTAINS)")
          @RequestParam Map<String, String> reqParam) ;

  /**
   * <p>
   * Retrieves operator info by id.
   * </p>
   *
   * @param id Unique identifier by id.
   * @return Operator info
   */
  @GetMapping("/{id}")
  @Operation(description = "Retrieve operator by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<OperatorDTO> getOperatorById(
      @PathVariable final Long id) ;

  /**
   * <p>
   * Retrieves a paginated list of Operators filtered by a name.
   * </p>
   *
   * @param text text to search
   * @param pageable pagination info
   * @return Paginated list of operators
   */
  @GetMapping("/search")
  @Operation(description = "Retrieve operator by filter", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<Page<OperatorDTO>> getOperatorsByFilter(
      @RequestParam final String text,
      Pageable pageable) ;

  /**
   * <p>
   * Creates a new Operator
   * </p>
   *
   * @param operatorDTO Operator info to create
   * @return Operator info created
   */
  @PostMapping("")
  @Operation(description = "Save a new operator", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<OperatorDTO> saveOperator(
      @RequestBody final OperatorDTO operatorDTO) ;

  /**
   * <p>
   * Updates operator info
   * </p>
   *
   * @param id         Unique identifier
   * @param operatorDTO Updated operator info
   * @return The updated operator
   */
  @PutMapping("/{id}")
  @Operation(description = "Updates existing operator", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<OperatorDTO> updateOperator(
      @Parameter(description = "Operator identifier") @NotNull @PathVariable final Long id,
      @Parameter(description = "Master Operator object") @NotNull @RequestBody final OperatorDTO operatorDTO) ;

  /**
   * <p>
   * Soft deletes an operator by id.
   * </p>
   *
   * @param id Unique identifier
   * @return No content
   */
  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing operator by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  ResponseEntity<?> deleteOperator(
      @Parameter(description = "Operator identifier") @PathVariable @NotNull final Long id) ;
}
