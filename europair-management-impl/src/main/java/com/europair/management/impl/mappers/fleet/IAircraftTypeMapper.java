package com.europair.management.impl.mappers.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IAircraftTypeMapper {

    IAircraftTypeMapper INSTANCE = Mappers.getMapper(IAircraftTypeMapper.class);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "averageSpeed", ignore = true)
    @Mapping(target = "observations", ignore = true)
    AircraftType toEntity(final AircraftTypeDto dto);

    @Mapping(target = "category.subcategories", ignore = true)
    @Mapping(target = "category.parentCategory", ignore = true)
    @Mapping(target = "subcategory.parentCategory", ignore = true)
    @Mapping(target = "subcategory.subcategories", ignore = true)
    AircraftTypeDto toDto(final AircraftType entity);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "averageSpeed", ignore = true)
    @Mapping(target = "observations", ignore = true)
    void updateFromDto(final AircraftTypeDto dto, @MappingTarget AircraftType entity);

}
