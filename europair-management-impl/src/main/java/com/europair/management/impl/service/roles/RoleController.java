package com.europair.management.impl.service.roles;

import com.europair.management.api.dto.roles.RoleDTO;
import com.europair.management.api.service.roles.IRoleController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoleController implements IRoleController {

  private final IRoleService IRoleService;

  public ResponseEntity<Page<RoleDTO>> getAllRolesPaginated(final Pageable pageable) {
    final Page<RoleDTO> pageRolesDTO = IRoleService.findAllPaginated(pageable);
    return ResponseEntity.ok().body(pageRolesDTO);
  }

  public ResponseEntity<RoleDTO> getRoleById(final Long id) {
    final RoleDTO roleDTO = IRoleService.findById(id);
    return ResponseEntity.ok().body(roleDTO);
  }

  public ResponseEntity<RoleDTO> saveRole(final RoleDTO roleDTO) {
    final RoleDTO roleDTOSaved = IRoleService.saveRole(roleDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(roleDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(roleDTOSaved);
  }

  public ResponseEntity<RoleDTO> updateRole(final Long id, final RoleDTO roleDTO) {
    final RoleDTO roleDTOSaved = IRoleService.updateRole(id, roleDTO);
    return ResponseEntity.ok().body(roleDTOSaved);
  }

  public ResponseEntity<?> deleteRole(final Long id) {
    IRoleService.deleteRole(id);
    return ResponseEntity.noContent().build();
  }

}
