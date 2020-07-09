package com.europair.management.rest.model.roles.mapper;

import com.europair.management.rest.model.roles.dto.RoleDTO;
import com.europair.management.rest.model.roles.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {

  RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

  RoleDTO toDto (final Role entity);

  List<RoleDTO> toListDtos (final List<Role> listEntities);

  Role toEntity (final RoleDTO roleDTO);

}
