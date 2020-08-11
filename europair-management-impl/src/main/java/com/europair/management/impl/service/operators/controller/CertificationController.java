package com.europair.management.impl.service.operators.controller;

import com.europair.management.api.dto.operators.dto.CertificationDTO;
import com.europair.management.api.service.operators.ICertificationController;
import com.europair.management.impl.service.operators.service.ICertificationService;
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
public class CertificationController implements ICertificationController {

  private final ICertificationService certificationService;

  @GetMapping("/operators/{operatorId}/certifications")
  @Operation(description = "Paged result of operator certifications list", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<CertificationDTO>> getAllCerticationsPaginated(final Pageable pageable, @PathVariable final Long operatorId) {

    final Page<CertificationDTO> pageCertificationsDTO = certificationService.findAllPaginated(pageable, operatorId);
    return ResponseEntity.ok().body(pageCertificationsDTO);

  }

  @GetMapping("/operators/{operatorId}/certifications/{id}")
  @Operation(description = "Retrieve operator certification by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<CertificationDTO> getCertificationById(@PathVariable final Long id, @PathVariable final Long operatorId) {

    final CertificationDTO certificationDTO = certificationService.findById(id, operatorId);
    return ResponseEntity.ok().body(certificationDTO);
  }

  @PostMapping("/operators/{operatorId}/certifications")
  @Operation(description = "Save a new operator certification", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<CertificationDTO> saveCertification(@RequestBody final CertificationDTO certificationDTO, @PathVariable final Long operatorId) {

    final CertificationDTO certificationDTOSaved = certificationService.saveCertification(certificationDTO, operatorId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(certificationDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(certificationDTOSaved);

  }

  @PutMapping("/operators/{operatorId}/certifications/{id}")
  @Operation(description = "Updates existing operator certification", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<CertificationDTO> updateOperator(@PathVariable final Long id, @RequestBody final CertificationDTO certificationDTO, @PathVariable final Long operatorId) {

    final CertificationDTO certificationDTOSaved = certificationService.updateCertification(id, certificationDTO, operatorId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(certificationDTOSaved.getId())
      .toUri();

    return ResponseEntity.ok().body(certificationDTOSaved);

  }

  @DeleteMapping("/operators/{operatorId}/certifications/{id}")
  @Operation(description = "Deletes existing operator certification by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteCertification(@PathVariable final Long id, @PathVariable final Long operatorId) {

    certificationService.deleteCertification(id, operatorId);
    return ResponseEntity.noContent().build();

  }

}
