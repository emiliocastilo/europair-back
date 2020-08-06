package com.europair.management.impl.service.roles.controller;

import com.europair.management.api.dto.roles.dto.RoleDTO;
import com.europair.management.api.service.roles.controller.IRoleController;
import com.europair.management.impl.service.roles.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
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
@RequestMapping("/roles")
public class RoleController implements IRoleController, InitializingBean {

  private final RoleService roleService;

  @GetMapping("")
  @Operation(description = "Paged result of master roles", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<RoleDTO>> getAllRolesPaginated(@Parameter(description = "Pagination filter")final Pageable pageable) {

    final Page<RoleDTO> pageRolesDTO = roleService.findAllPaginated(pageable);
    return ResponseEntity.ok().body(pageRolesDTO);

  }

  @GetMapping("/{id}")
  @Operation(description = "Retrieve master role by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<RoleDTO> getRoleById(@Parameter(description = "Role identifier") @NotNull @PathVariable final Long id) {
    final RoleDTO roleDTO = roleService.findById(id);
    return ResponseEntity.ok().body(roleDTO);
  }

  @PostMapping("")
  @Operation(description = "Save a new master role", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<RoleDTO> saveRole(@Parameter(description = "Master Role object") @NotNull @RequestBody final RoleDTO roleDTO) {

    final RoleDTO roleDTOSaved = roleService.saveRole(roleDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(roleDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(roleDTOSaved);

  }

  @PutMapping("/{id}")
  @Operation(description = "Updates existing master role", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<RoleDTO> updateRole(@Parameter(description = "Role identifier") @NotNull @PathVariable final Long id,
                                            @Parameter(description = "Master Role object") @NotNull @RequestBody final RoleDTO roleDTO) {

    final RoleDTO roleDTOSaved = roleService.updateRole(id, roleDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(roleDTOSaved.getId())
      .toUri();

    return ResponseEntity.ok().body(roleDTOSaved);

  }

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing master role by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteRole(@Parameter(description = "Role identifier") @PathVariable @NotNull final Long id) {

    roleService.deleteRole(id);
    return ResponseEntity.noContent().build();

  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("estamos escaneando");
  }
}
