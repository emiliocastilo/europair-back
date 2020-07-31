package com.europair.management.rest.model.users.mapper;

import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.users.dto.UserDTO;
import com.europair.management.rest.model.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDTO toDto (final User entity);

  List<UserDTO> toListDtos (final List<User> listEntities);

  User toEntity (final UserDTO userDTO);

  void updateFromDto(final UserDTO userDTO, @MappingTarget User user);
}
