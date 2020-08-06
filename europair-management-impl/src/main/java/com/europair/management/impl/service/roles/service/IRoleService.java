package com.europair.management.impl.service.roles.service;

import com.europair.management.api.dto.roles.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {

  Page<RoleDTO> findAllPaginated(Pageable pageable);
  RoleDTO findById(Long id);
  RoleDTO saveRole(RoleDTO roleDTO);
  RoleDTO updateRole(Long id, RoleDTO roleDTO);
  void deleteRole(Long id);

}
