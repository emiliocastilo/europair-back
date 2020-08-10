package com.europair.management.impl.service.roles;

import com.europair.management.api.dto.roles.dto.RoleDTO;
import com.europair.management.api.service.roles.IRoleController;
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
@RequestMapping("/roles")
public class RoleController implements IRoleController {

  private final IRoleService IRoleService;

  @GetMapping("")
  public ResponseEntity<Page<RoleDTO>> getAllRolesPaginated(final Pageable pageable) {

    final Page<RoleDTO> pageRolesDTO = IRoleService.findAllPaginated(pageable);
    return ResponseEntity.ok().body(pageRolesDTO);

  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleDTO> getRoleById(final Long id) {
    final RoleDTO roleDTO = IRoleService.findById(id);
    return ResponseEntity.ok().body(roleDTO);
  }

  @PostMapping("")
  public ResponseEntity<RoleDTO> saveRole(final RoleDTO roleDTO) {

    final RoleDTO roleDTOSaved = IRoleService.saveRole(roleDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(roleDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(roleDTOSaved);

  }

  @PutMapping("/{id}")
  public ResponseEntity<RoleDTO> updateRole(final Long id, final RoleDTO roleDTO) {

    final RoleDTO roleDTOSaved = IRoleService.updateRole(id, roleDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(roleDTOSaved.getId())
      .toUri();

    return ResponseEntity.ok().body(roleDTOSaved);

  }

  @DeleteMapping("/{id}")

  public ResponseEntity<?> deleteRole(final Long id) {

    IRoleService.deleteRole(id);
    return ResponseEntity.noContent().build();

  }

}
