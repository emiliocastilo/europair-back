package com.europair.management.impl.mappers.fleet;


import com.europair.management.api.dto.fleet.AircraftCategoryDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface AircraftCategoryMapper {

    AircraftCategoryMapper INSTANCE = Mappers.getMapper(AircraftCategoryMapper.class);

    @Mapping(target = "subcategories", qualifiedByName = "aircraftCategoryNoParent")
    @Mapping(target = "parentCategory", ignore = true)
    AircraftCategoryDto toDtoWithSubcategories(final AircraftCategory category);

    @Named("aircraftCategoryNoParent")
    @Mapping(target = "parentCategory", ignore = true)
    AircraftCategoryDto toDtoNoParent(final AircraftCategory category);

    List<AircraftCategoryDto> toDto(final List<AircraftCategory> entityList);

    AircraftCategory toEntity(final AircraftCategoryDto aircraftCategoryDto);

    @Mapping(target = "parentCategory", ignore = true)
    void updateFromDto(final AircraftCategoryDto aircraftCategoryDto, @MappingTarget AircraftCategory aircraftCategory);
}
