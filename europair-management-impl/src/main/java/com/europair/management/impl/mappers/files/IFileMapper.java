package com.europair.management.impl.mappers.files;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.files.entity.File;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IFileMapper {

  IFileMapper INSTANCE = Mappers.getMapper(IFileMapper.class);

  FileDTO toDto(final File entity);

  File toEntity(final FileDTO fileDTO);

  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "code", ignore = true)
  @Mapping(target = "statusId", ignore = true)
  void updateFromDto(final FileDTO fileDTO, @MappingTarget File file);

}
