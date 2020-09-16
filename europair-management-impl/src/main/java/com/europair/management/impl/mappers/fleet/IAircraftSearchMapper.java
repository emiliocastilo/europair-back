package com.europair.management.impl.mappers.fleet;

import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = IAircraftBaseMapper.class)
public interface IAircraftSearchMapper {

    IAircraftSearchMapper INSTANCE = Mappers.getMapper(IAircraftSearchMapper.class);

    @Mapping(target = "bases", qualifiedByName = "toAircraftBaseSimpleDto")
    @Mapping(target = "aircraftType.category.subcategories", ignore = true)
    @Mapping(target = "aircraftType.category.parentCategory", ignore = true)
    @Mapping(target = "aircraftType.subcategory.parentCategory", ignore = true)
    @Mapping(target = "aircraftType.subcategory.subcategories", ignore = true)
    @Mapping(target = "aircraftType.averageSpeed", ignore = true)
    @Mapping(target = "aircraftType.observations", ignore = true)
    @Mapping(target = "daytimeConfiguration", source = "entity", qualifiedByName = "seatingToDayTimeConfig")
    @Mapping(target = "nighttimeConfiguration", source = "entity", qualifiedByName = "mapNightTimeConfig")
    AircraftSearchResultDataDto toDto(final Aircraft entity);


    @Named("seatingToDayTimeConfig")
    default Integer sumSeating(Aircraft entity) {
        return (entity.getSeatingF() == null ? 0 : entity.getSeatingF()) +
                (entity.getSeatingC() == null ? 0 : entity.getSeatingC()) +
                (entity.getSeatingY() == null ? 0 : entity.getSeatingY());
    }

    @Named("mapNightTimeConfig")
    default Integer mapNighttimeConfig(Aircraft entity) {
        return entity.getNighttimeConfiguration() != null ? entity.getNighttimeConfiguration() :
                (entity.getSeatingF() == null ? 0 : entity.getSeatingF()) +
                        (entity.getSeatingC() == null ? 0 : entity.getSeatingC()) +
                        (entity.getSeatingY() == null ? 0 : entity.getSeatingY());
    }
}
