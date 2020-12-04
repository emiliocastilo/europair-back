package com.europair.management.impl.service.roles;

import com.europair.management.api.dto.roles.RoleDTO;
import com.europair.management.api.service.roles.IRoleController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoleController implements IRoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private final IRoleService IRoleService;

    public ResponseEntity<Page<RoleDTO>> getAllRolesPaginated(final Pageable pageable) {
        LOGGER.debug("[RoleController] - Starting method [getAllRolesPaginated] with input: pageable={}", pageable);
        final Page<RoleDTO> pageRolesDTO = IRoleService.findAllPaginated(pageable);
        LOGGER.debug("[RoleController] - Ending method [getAllRolesPaginated] with return: {}", pageRolesDTO);
        return ResponseEntity.ok().body(pageRolesDTO);
    }

    public ResponseEntity<RoleDTO> getRoleById(final Long id) {
        LOGGER.debug("[RoleController] - Starting method [getRoleById] with input: id={}", id);
        final RoleDTO roleDTO = IRoleService.findById(id);
        LOGGER.debug("[RoleController] - Ending method [getRoleById] with return: {}", roleDTO);
        return ResponseEntity.ok().body(roleDTO);
    }

    public ResponseEntity<RoleDTO> saveRole(final RoleDTO roleDTO) {
        LOGGER.debug("[RoleController] - Starting method [saveRole] with input: roleDTO={}", roleDTO);
        final RoleDTO roleDTOSaved = IRoleService.saveRole(roleDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(roleDTOSaved.getId())
                .toUri();
        LOGGER.debug("[RoleController] - Ending method [saveRole] with return: {}", roleDTOSaved);
        return ResponseEntity.created(location).body(roleDTOSaved);
    }

    public ResponseEntity<RoleDTO> updateRole(final Long id, final RoleDTO roleDTO) {
        LOGGER.debug("[RoleController] - Starting method [updateRole] with input: id={}, roleDTO={}", id, roleDTO);
        final RoleDTO roleDTOSaved = IRoleService.updateRole(id, roleDTO);
        LOGGER.debug("[RoleController] - Ending method [updateRole] with return: {}", roleDTOSaved);
        return ResponseEntity.ok().body(roleDTOSaved);
    }

    public ResponseEntity<?> deleteRole(final Long id) {
        LOGGER.debug("[RoleController] - Starting method [deleteRole] with input: id={}", id);
        IRoleService.deleteRole(id);
        LOGGER.debug("[RoleController] - Ending method [deleteRole] with no return.");
        return ResponseEntity.noContent().build();
    }

}
