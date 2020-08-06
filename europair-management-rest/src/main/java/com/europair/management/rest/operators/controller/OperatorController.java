package com.europair.management.rest.operators.controller;

import com.europair.management.rest.model.operators.dto.OperatorDTO;
import com.europair.management.rest.operators.service.OperatorService;
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
@RequestMapping("/operators")
public class OperatorController {

  private final OperatorService operatorService;


  @GetMapping("")
  public ResponseEntity<Page<OperatorDTO>> getAllOperatorsPaginated(final Pageable pageable) {

    final Page<OperatorDTO> pageOperatorsDTO = operatorService.findAllPaginated(pageable);
    return ResponseEntity.ok().body(pageOperatorsDTO);

  }

  @GetMapping("/{id}")
  public ResponseEntity<OperatorDTO> getOperatorById(@PathVariable final Long id) {

    final OperatorDTO operatorDTO = operatorService.findById(id);
    return ResponseEntity.ok().body(operatorDTO);
  }

  @PostMapping("")
  public ResponseEntity<OperatorDTO> saveOperator(@RequestBody final OperatorDTO operatorDTO) {

    final OperatorDTO operatorDTOSaved = operatorService.saveOperator(operatorDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(operatorDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(operatorDTOSaved);

  }

  @PutMapping("/{id}")
  public ResponseEntity<OperatorDTO> updateOperator(@PathVariable final Long id, @RequestBody final OperatorDTO operatorDTO) {

    final OperatorDTO operatorDTOSaved = operatorService.updateOperator(id, operatorDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(operatorDTOSaved.getId())
      .toUri();

    return ResponseEntity.ok().body(operatorDTOSaved);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteOperator(@PathVariable final Long id) {

    operatorService.deleteOperator(id);
    return ResponseEntity.noContent().build();

  }
}
