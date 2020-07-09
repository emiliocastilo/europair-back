package com.europair.management.rest.roles.controller;

import com.europair.management.rest.model.roles.dto.RoleDTO;
import com.europair.management.rest.roles.service.RoleService;
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
public class RoleController {

  private final RoleService roleService;

  @GetMapping("")
  public ResponseEntity<Page<RoleDTO>> getAllRolesPaginated(final Pageable pageable) {

    final Page<RoleDTO> pageRolesDTO = roleService.findAllPaginated(pageable);
    return ResponseEntity.ok().body(pageRolesDTO);

  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleDTO> getRoleById(@PathVariable final Long id) {
    final RoleDTO roleDTO = roleService.findById(id);
    return ResponseEntity.ok().body(roleDTO);
  }

  @PostMapping("")
  public ResponseEntity<RoleDTO> saveRole(@RequestBody final RoleDTO roleDTO) {

    final RoleDTO roleDTOSaved = roleService.saveRole(roleDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(roleDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(roleDTOSaved);

  }

}
