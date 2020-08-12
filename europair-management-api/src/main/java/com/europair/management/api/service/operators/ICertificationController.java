package com.europair.management.api.service.operators;


import com.europair.management.api.dto.operators.CertificationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ICertificationController {


  @GetMapping("/operator/{operatorId}/certifications")
  @Operation(description = "Paged result of operator certifications list", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<CertificationDTO>> getAllCerticationsPaginated(final Pageable pageable, @PathVariable final Long operatorId) ;

  @GetMapping("/operator/{operatorId}/certifications/{id}")
  @Operation(description = "Retrieve operator certification by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<CertificationDTO> getCertificationById(@PathVariable final Long id, @PathVariable final Long operatorId) ;

  @PostMapping("/operator/{operatorId}/certifications")
  @Operation(description = "Save a new operator certification", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<CertificationDTO> saveCertification(@RequestBody final CertificationDTO certificationDTO, @PathVariable final Long operatorId) ;

  @PutMapping("/operator/{operatorId}/certifications/{id}")
  @Operation(description = "Updates existing operator certification", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<CertificationDTO> updateOperator(@PathVariable final Long id, @RequestBody final CertificationDTO certificationDTO, @PathVariable final Long operatorId) ;

  @DeleteMapping("/operator/{operatorId}/certifications/{id}")
  @Operation(description = "Deletes existing operator certification by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteCertification(@PathVariable final Long id, @PathVariable final Long operatorId) ;

}
