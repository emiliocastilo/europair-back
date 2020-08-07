package com.europair.management.impl.service.roles.service.impl;

import com.europair.management.api.dto.roles.dto.RoleDTO;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.roles.RoleMapper;
import com.europair.management.impl.service.roles.service.IRoleService;
import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.roles.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

  private final IRoleRepository roleRepository;

  @Override
  public Page<RoleDTO> findAllPaginated(Pageable pageable) {
    return roleRepository.findAll(pageable).map(role -> RoleMapper.INSTANCE.toDto(role));
  }

  @Override
  public RoleDTO findById(final Long id) throws ResourceNotFoundException {
    return RoleMapper.INSTANCE.toDto(roleRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Role not found on id: " + id)));
  }

  @Transactional(readOnly = false)
  @Override
  public RoleDTO saveRole(final RoleDTO roleDTO) {

    if(roleDTO.getId() != null){
        throw new InvalidArgumentException(String.format("New role expected. Identifier %s got", roleDTO.getId()));
    }
    Role role = RoleMapper.INSTANCE.toEntity(roleDTO);
    role = roleRepository.save(role);
    return RoleMapper.INSTANCE.toDto(role);
  }

  @Transactional(readOnly = false)
  @Override
  public RoleDTO updateRole(final Long id, final RoleDTO roleDTO) {

    Role role = roleRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Role not found on id: " + id));

    RoleMapper.INSTANCE.updateFromDto(roleDTO, role);
    role = roleRepository.save(role);

    return RoleMapper.INSTANCE.toDto(role);
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteRole(Long id) {

    Role roleBD = roleRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Role not found on id: " + id));
    roleRepository.deleteById(id);
  }


}
