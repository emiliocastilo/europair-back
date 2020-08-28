package com.europair.management.impl.mappers.files;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.files.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IFileMapper {

  IFileMapper INSTANCE = Mappers.getMapper(IFileMapper.class);

  FileDTO toDto(final File entity);

  File toEntity(final FileDTO fileDTO);

  void updateFromDto(final FileDTO fileDTO, @MappingTarget File file);

}
