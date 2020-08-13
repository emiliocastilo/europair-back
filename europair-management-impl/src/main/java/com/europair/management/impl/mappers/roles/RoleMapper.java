package com.europair.management.impl.mappers.roles;

import com.europair.management.api.dto.fleet.AircraftBaseDto;
import com.europair.management.api.dto.roles.RoleDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.roles.entity.Role;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface RoleMapper {

  RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

  RoleDTO toDto (final Role entity);

  List<RoleDTO> toListDtos (final List<Role> listEntities);

  Role toEntity (final RoleDTO roleDTO);

  void updateFromDto(final RoleDTO roleDTO, @MappingTarget Role role);

  @Named("roleNoTask")
  @Mapping(target = "tasks", ignore = true)
  Role toEntityWithoutTasks(final RoleDTO dto);

}
