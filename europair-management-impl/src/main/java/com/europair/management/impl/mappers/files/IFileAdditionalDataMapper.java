package com.europair.management.impl.mappers.files;

import com.europair.management.api.dto.files.FileAdditionalDataDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.files.entity.FileAdditionalData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IFileAdditionalDataMapper {

    IFileAdditionalDataMapper INSTANCE = Mappers.getMapper(IFileAdditionalDataMapper.class);

    @Mapping(target = "file", ignore = true)
    FileAdditionalDataDto toDto(final FileAdditionalData entity);

    @Mapping(target = "file", ignore = true)
    FileAdditionalData toEntity(final FileAdditionalDataDto dto);

    @Mapping(target = "fileId", ignore = true)
    void updateFromDto(final FileAdditionalDataDto dto, @MappingTarget FileAdditionalData entity);

}
