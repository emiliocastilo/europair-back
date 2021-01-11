package com.europair.management.impl.mappers.users;

import com.europair.management.api.dto.roles.RoleDTO;
import com.europair.management.api.dto.users.UserDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.impl.mappers.roles.RoleMapper;
import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.users.entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = RoleMapper.class)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDTO toDto (final User entity);

  List<UserDTO> toListDtos (final List<User> listEntities);

  User toEntity (final UserDTO userDTO);

  @Mapping(target = "roles", qualifiedByName = "toRoleNoTask")
  void updateFromDto(final UserDTO userDTO, @MappingTarget User user);

  @Named("toRoleNoTask")
  @IterableMapping(qualifiedByName = "roleNoTask")
  List<Role> mapRolesNoTask(final List<RoleDTO> dto);
}
