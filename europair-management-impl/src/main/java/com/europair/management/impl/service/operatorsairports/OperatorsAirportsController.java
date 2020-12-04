package com.europair.management.impl.service.operatorsairports;

import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.impl.service.operators.OperatorController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping(value = {"/operators/{operatorId}/airports", "/external/operators/{operatorId}/airports"})
public class OperatorsAirportsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorController.class);

    @Autowired
    private IOperatorsAirportsService operatorsAirportsService;

    @GetMapping("")
    @Operation(description = "Paged result of airports list", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<OperatorsAirportsDTO>> getAllAirportsByOperatorPaginated(
            @PathVariable final Long operatorId,
            final Pageable pageable) {
        LOGGER.debug("[OperatorsAirportsController] - Starting method [getAllAirportsByOperatorPaginated] with input: operatorId={}, pageable={}",
                operatorId, pageable);
        final Page<OperatorsAirportsDTO> pageOperatorsAirportsDTO = operatorsAirportsService
                .findOperatorsAirportsByOperatorPaginated(operatorId, pageable);
        LOGGER.debug("[OperatorsAirportsController] - Ending method [getAllAirportsByOperatorPaginated] with return: {}", pageOperatorsAirportsDTO);
        return ResponseEntity.ok().body(pageOperatorsAirportsDTO);
    }

    @GetMapping("/{id}")
    @Operation(description = "Paged result of airports list", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<OperatorsAirportsDTO> getOperatorsAirportsById(
            @PathVariable final Long operatorId,
            @PathVariable final Long id) {
        LOGGER.debug("[OperatorsAirportsController] - Starting method [getOperatorsAirportsById] with input: id={}, operatorId={}", id, operatorId);
        final OperatorsAirportsDTO operatorsAirportsDTO = operatorsAirportsService.findOperatorsAirportsById(operatorId, id);
        LOGGER.debug("[OperatorsAirportsController] - Ending method [getOperatorsAirportsById] with return: {}", operatorsAirportsDTO);
        return ResponseEntity.ok().body(operatorsAirportsDTO);
    }

    @PostMapping("")
    @Operation(description = "Assign Airports to Operator", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<OperatorsAirportsDTO> saveOperatorsAirports(
            @PathVariable final Long operatorId,
            @RequestBody final OperatorsAirportsDTO operatorsAirportsDTO) {
        LOGGER.debug("[OperatorsAirportsController] - Starting method [saveOperatorsAirports] with input: operatorId={}, operatorsAirportsDTO={}",
                operatorId, operatorsAirportsDTO);
        final OperatorsAirportsDTO operatorsAirportsDTOSaved = operatorsAirportsService.saveOperatorsAirports(operatorId, operatorsAirportsDTO);
        LOGGER.debug("[OperatorsAirportsController] - Ending method [saveOperatorsAirports] with return: {}", operatorsAirportsDTOSaved);
        return ResponseEntity.ok().body(operatorsAirportsDTOSaved);
    }

    @PutMapping("/{id}")
    @Operation(description = "Edit Airports assignment to Operator", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<OperatorsAirportsDTO> updateOperatorsAirports(
            @PathVariable final Long operatorId,
            @PathVariable final Long id,
            @RequestBody final OperatorsAirportsDTO operatorsAirportsDTO) {
        LOGGER.debug("[OperatorsAirportsController] - Starting method [updateOperatorsAirports] with input: id={}, operatorId={}, operatorsAirportsDTO={}",
                id, operatorId, operatorsAirportsDTO);
        final OperatorsAirportsDTO operatorsAirportsDTOUpdated = operatorsAirportsService.updateOperatorsAirports(operatorId, id, operatorsAirportsDTO);
        LOGGER.debug("[OperatorsAirportsController] - Ending method [updateOperatorsAirports] with return: {}", operatorsAirportsDTOUpdated);
        return ResponseEntity.ok().body(operatorsAirportsDTOUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete Airports assignment to Operator", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<?> deleteOperatorsAirports(
            @Parameter(description = "Operator identifier") @PathVariable @NotNull final Long operatorId,
            @Parameter(description = "OperatorsAirports identifier") @PathVariable @NotNull final Long id) {
        LOGGER.debug("[OperatorsAirportsController] - Starting method [deleteOperatorsAirports] with input: id={}, operatorId={}", id, operatorId);
        operatorsAirportsService.deleteOperatorsAirports(operatorId, id);
        LOGGER.debug("[OperatorsAirportsController] - Ending method [deleteOperatorsAirports] with no return.");
        return ResponseEntity.noContent().build();
    }

}
