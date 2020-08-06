package com.europair.management.rest.operators.controller;

import com.europair.management.rest.model.operators.dto.CertificationDTO;
import com.europair.management.rest.model.operators.dto.OperatorDTO;
import com.europair.management.rest.operators.service.CertificationService;
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
public class CertificationController {

  private final CertificationService certificationService;

  @GetMapping("/operator/{operatorId}/certifications")
  public ResponseEntity<Page<CertificationDTO>> getAllCerticationsPaginated(final Pageable pageable, @PathVariable final Long operatorId) {

    final Page<CertificationDTO> pageCertificationsDTO = certificationService.findAllPaginated(pageable, operatorId);
    return ResponseEntity.ok().body(pageCertificationsDTO);

  }

  @GetMapping("/operator/{operatorId}/certifications/{id}")
  public ResponseEntity<CertificationDTO> getCertificationById(@PathVariable final Long id, @PathVariable final Long operatorId) {

    final CertificationDTO certificationDTO = certificationService.findById(id, operatorId);
    return ResponseEntity.ok().body(certificationDTO);
  }

  @PostMapping("/operator/{operatorId}/certifications")
  public ResponseEntity<CertificationDTO> saveCertification(@RequestBody final CertificationDTO certificationDTO, @PathVariable final Long operatorId) {

    final CertificationDTO certificationDTOSaved = certificationService.saveCertification(certificationDTO, operatorId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(certificationDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(certificationDTOSaved);

  }

  @PutMapping("/operator/{operatorId}/certifications/{id}")
  public ResponseEntity<CertificationDTO> updateOperator(@PathVariable final Long id, @RequestBody final CertificationDTO certificationDTO, @PathVariable final Long operatorId) {

    final CertificationDTO certificationDTOSaved = certificationService.updateCertification(id, certificationDTO, operatorId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(certificationDTOSaved.getId())
      .toUri();

    return ResponseEntity.ok().body(certificationDTOSaved);

  }

  @DeleteMapping("/operator/{operatorId}/certifications/{id}")
  public ResponseEntity<?> deleteCertification(@PathVariable final Long id, @PathVariable final Long operatorId) {

    certificationService.deleteCertification(id, operatorId);
    return ResponseEntity.noContent().build();

  }

}
