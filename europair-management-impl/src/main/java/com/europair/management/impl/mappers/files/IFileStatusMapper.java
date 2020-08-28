package com.europair.management.impl.mappers.files;

import com.europair.management.api.dto.files.FileStatusDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.files.entity.FileStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IFileStatusMapper {

    IFileStatusMapper INSTANCE = Mappers.getMapper(IFileStatusMapper.class);

    FileStatusDto toDto(final FileStatus entity);

    FileStatus toEntity(final FileStatusDto fileStatusDto);

    void updateFromDto(final FileStatusDto fileStatusDto, @MappingTarget FileStatus fileStatus);

}
