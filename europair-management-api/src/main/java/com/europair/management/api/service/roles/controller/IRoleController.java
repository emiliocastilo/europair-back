package com.europair.management.api.service.roles.controller;

import com.europair.management.api.dto.roles.dto.RoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RequestMapping("/roles")
public interface IRoleController {

  @GetMapping("")
  @Operation(description = "Paged result of master roles", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<RoleDTO>> getAllRolesPaginated(@Parameter(description = "Pagination filter")final Pageable pageable) ;

  @GetMapping("/{id}")
  @Operation(description = "Retrieve master role by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<RoleDTO> getRoleById(@Parameter(description = "Role identifier") @NotNull @PathVariable final Long id) ;

  @PostMapping("")
  @Operation(description = "Save a new master role", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<RoleDTO> saveRole(@Parameter(description = "Master Role object") @NotNull @RequestBody final RoleDTO roleDTO) ;

  @PutMapping("/{id}")
  @Operation(description = "Updates existing master role", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<RoleDTO> updateRole(@Parameter(description = "Role identifier") @NotNull @PathVariable final Long id,
                                            @Parameter(description = "Master Role object") @NotNull @RequestBody final RoleDTO roleDTO) ;

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing master role by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteRole(@Parameter(description = "Role identifier") @PathVariable @NotNull final Long id) ;

}
