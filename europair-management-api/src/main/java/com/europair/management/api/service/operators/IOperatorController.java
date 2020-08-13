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


@RequestMapping("/operators")
public interface IOperatorController {


  @GetMapping("")
  @Operation(description = "Paged result of operators list", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<OperatorDTO>> getAllOperatorsPaginated(final Pageable pageable) ;

  @GetMapping("/{id}")
  @Operation(description = "Retrieve operator by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorDTO> getOperatorById(@PathVariable final Long id) ;

  @GetMapping("/search")
  @Operation(description = "Retrieve operator by filter", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<OperatorDTO>> getOperatorsByFilter(@RequestParam final String text, Pageable pageable) ;

  @PostMapping("")
  @Operation(description = "Save a new operator", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorDTO> saveOperator(@RequestBody final OperatorDTO operatorDTO) ;

  @PutMapping("/{id}")
  @Operation(description = "Updates existing operator", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorDTO> updateOperator(@Parameter(description = "Operator identifier") @NotNull @PathVariable final Long id,
                                                    @Parameter(description = "Master Operator object") @NotNull @RequestBody final OperatorDTO operatorDTO) ;

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing operator by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteOperator(@Parameter(description = "Operator identifier") @PathVariable @NotNull final Long id) ;
}
