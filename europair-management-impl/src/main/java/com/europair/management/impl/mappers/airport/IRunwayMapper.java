package com.europair.management.impl.mappers.airport;

import com.europair.management.api.dto.airport.RunwayDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.airport.entity.Runway;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IRunwayMapper {

    IRunwayMapper INSTANCE = Mappers.getMapper(IRunwayMapper.class);

    @Named("toRunwayDto")
    @Mapping(target = "length.value", source = "length")
    @Mapping(target = "length.type", source = "lengthUnit")
    @Mapping(target = "width.value", source = "width")
    @Mapping(target = "width.type", source = "widthUnit")
    RunwayDto toDto(final Runway entity);

    @Mapping(source = "length.value", target = "length")
    @Mapping(source = "length.type", target = "lengthUnit")
    @Mapping(source = "width.value", target = "width")
    @Mapping(source = "width.type", target = "widthUnit")
    Runway toEntity(final RunwayDto dto);

    @Mapping(source = "dto.length.value", target = "length")
    @Mapping(source = "dto.length.type", target = "lengthUnit")
    @Mapping(source = "dto.width.value", target = "width")
    @Mapping(source = "dto.width.type", target = "widthUnit")
    void updateFromDto(final RunwayDto dto, @MappingTarget Runway entity);

}
