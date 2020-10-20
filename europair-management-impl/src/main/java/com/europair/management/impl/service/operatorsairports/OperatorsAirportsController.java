package com.europair.management.impl.service.operatorsairports;

import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping(value = {"/operators/{operatorId}/airports", "/external/operators/{operatorId}/airports"})
public class OperatorsAirportsController {

  @Autowired
  private IOperatorsAirportsService operatorsAirportsService;

  @GetMapping("")
  @Operation(description = "Paged result of airports list", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<OperatorsAirportsDTO>> getAllAirportsByOperatorPaginated(
      @PathVariable final Long operatorId,
      final Pageable pageable) {

    final Page<OperatorsAirportsDTO> pageOperatorsAirportsDTO = operatorsAirportsService
      .findOperatorsAirportsByOperatorPaginated(operatorId, pageable);
    return ResponseEntity.ok().body(pageOperatorsAirportsDTO);

  }

  @GetMapping("/{id}")
  @Operation(description = "Paged result of airports list", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorsAirportsDTO> getOperatorsAirportsById(
    @PathVariable final Long operatorId,
    @PathVariable final Long id) {

    final OperatorsAirportsDTO operatorsAirportsDTO = operatorsAirportsService.findOperatorsAirportsById(operatorId, id);
    return ResponseEntity.ok().body(operatorsAirportsDTO);

  }

  @PostMapping("")
  @Operation(description = "Assign Airports to Operator", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorsAirportsDTO> saveOperatorsAirports (
      @PathVariable final Long operatorId,
      @RequestBody final OperatorsAirportsDTO operatorsAirportsDTO) {

    final OperatorsAirportsDTO operatorsAirportsDTOSaved = operatorsAirportsService.saveOperatorsAirports(operatorId, operatorsAirportsDTO);
    return ResponseEntity.ok().body(operatorsAirportsDTOSaved);

  }

  @PutMapping("/{id}")
  @Operation(description = "Edit Airports assignment to Operator", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<OperatorsAirportsDTO> updateOperatorsAirports (
      @PathVariable final Long operatorId,
      @PathVariable final Long id,
      @RequestBody final OperatorsAirportsDTO operatorsAirportsDTO) {

    final OperatorsAirportsDTO operatorsAirportsDTOUpdated = operatorsAirportsService.updateOperatorsAirports(operatorId, id, operatorsAirportsDTO);
    return ResponseEntity.ok().body(operatorsAirportsDTOUpdated);

  }

  @DeleteMapping("/{id}")
  @Operation(description = "Delete Airports assignment to Operator", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteOperatorsAirports(
        @Parameter(description = "Operator identifier") @PathVariable @NotNull final Long operatorId,
        @Parameter(description = "OperatorsAirports identifier") @PathVariable @NotNull final Long id) {

    operatorsAirportsService.deleteOperatorsAirports(operatorId, id);
    return ResponseEntity.noContent().build();

  }


}
