package com.europair.management.impl.service.operators;


import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.api.service.operators.IOperatorController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/operators")
public class OperatorController implements IOperatorController {

  private final IOperatorService operatorService;


  @GetMapping("")
  @Operation(description = "Paged result of operators list", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<OperatorDTO>> getAllOperatorsPaginated(final Pageable pageable) {

    final Page<OperatorDTO> pageOperatorsDTO = operatorService.findAllPaginated(pageable);
    return ResponseEntity.ok().body(pageOperatorsDTO);

  }

  @GetMapping("/{id}")
  @Operation(description = "Retrieve operator by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorDTO> getOperatorById(@PathVariable final Long id) {

    final OperatorDTO operatorDTO = operatorService.findById(id);
    return ResponseEntity.ok().body(operatorDTO);
  }

  @GetMapping("/search")
  @Operation(description = "Retrieve operator by filter", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<OperatorDTO>> getOperatorsByFilter(@RequestParam final String text, Pageable pageable) {

    final Page<OperatorDTO> pageOperatorsDTO = operatorService.searchOperator(text, pageable);
    return ResponseEntity.ok().body(pageOperatorsDTO);
  }

  @PostMapping("")
  @Operation(description = "Save a new operator", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorDTO> saveOperator(@RequestBody final OperatorDTO operatorDTO) {

    final OperatorDTO operatorDTOSaved = operatorService.saveOperator(operatorDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(operatorDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(operatorDTOSaved);

  }

  @PutMapping("/{id}")
  @Operation(description = "Updates existing operator", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorDTO> updateOperator(@Parameter(description = "Operator identifier") @NotNull @PathVariable final Long id,
                                                    @Parameter(description = "Master Operator object") @NotNull @RequestBody final OperatorDTO operatorDTO) {

    final OperatorDTO operatorDTOUpdated = operatorService.updateOperator(id, operatorDTO);

    return ResponseEntity.ok().body(operatorDTOUpdated);

  }

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing operator by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteOperator(@Parameter(description = "Operator identifier") @PathVariable @NotNull final Long id) {

    operatorService.deleteOperator(id);
    return ResponseEntity.noContent().build();

  }

}
