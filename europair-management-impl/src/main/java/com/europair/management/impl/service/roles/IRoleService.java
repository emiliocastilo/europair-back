package com.europair.management.impl.service.roles;

import com.europair.management.api.dto.roles.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {

  Page<RoleDTO> findAllPaginated(Pageable pageable);
  RoleDTO findById(Long id);
  RoleDTO saveRole(RoleDTO roleDTO);
  RoleDTO updateRole(Long id, RoleDTO roleDTO);
  void deleteRole(Long id);

}
