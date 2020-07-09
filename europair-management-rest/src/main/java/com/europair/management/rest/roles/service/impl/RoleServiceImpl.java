package com.europair.management.rest.roles.service.impl;

import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.roles.dto.RoleDTO;
import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.roles.mapper.RoleMapper;
import com.europair.management.rest.roles.repository.RoleRepository;
import com.europair.management.rest.roles.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Override
  public Page<RoleDTO> findAllPaginated(Pageable pageable) {
    return roleRepository.findAll(pageable).map(role -> RoleMapper.INSTANCE.toDto(role));
  }

  @Override
  public RoleDTO findById(final Long id) throws ResourceNotFoundException {
    return RoleMapper.INSTANCE.toDto(roleRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Role not found on id: " + id)));
  }

  @Override
  public RoleDTO saveRole(final RoleDTO roleDTO) {
    Role role = RoleMapper.INSTANCE.toEntity(roleDTO);
    role = roleRepository.save(role);
    return RoleMapper.INSTANCE.toDto(role);
  }

  @Override
  public RoleDTO updateRole(final Long id, final RoleDTO roleDTO) {

    Role roleBD = roleRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Role not found on id: " + id));

    RoleDTO roleDTO2Update = updateRoleValues(roleDTO);

    Role role = RoleMapper.INSTANCE.toEntity(roleDTO2Update);
    role = roleRepository.save(role);

    return RoleMapper.INSTANCE.toDto(role);
  }

  @Override
  public void deleteRole(Long id) {

    Role roleBD = roleRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Role not found on id: " + id));
    roleRepository.deleteById(id);
  }

  private RoleDTO updateRoleValues(RoleDTO roleDTO) {

    return RoleDTO.builder()
      .id(roleDTO.getId())
      .name(roleDTO.getName())
      .description(roleDTO.getDescription())
      .tasks(roleDTO.getTasks())
      .build();

  }

}
