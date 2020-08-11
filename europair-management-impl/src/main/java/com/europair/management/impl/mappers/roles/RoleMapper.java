package com.europair.management.impl.mappers.roles;

import com.europair.management.api.dto.roles.dto.RoleDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.roles.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface RoleMapper {

  RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

  RoleDTO toDto (final Role entity);

  List<RoleDTO> toListDtos (final List<Role> listEntities);

  Role toEntity (final RoleDTO roleDTO);

  void updateFromDto(final RoleDTO roleDTO, @MappingTarget Role role);

}
