package com.europair.management.rest.roles.service;

import com.europair.management.rest.model.roles.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

  Page<RoleDTO> findAllPaginated(Pageable pageable);
  RoleDTO findById(Long id);
  RoleDTO saveRole(RoleDTO roleDTO);
  RoleDTO updateRole(Long id, RoleDTO roleDTO);
  void deleteRole(Long id);

}
