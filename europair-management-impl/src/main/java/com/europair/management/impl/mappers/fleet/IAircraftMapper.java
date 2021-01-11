package com.europair.management.impl.mappers.fleet;

import com.europair.management.api.dto.fleet.AircraftBaseDto;
import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
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
        uses = IAircraftBaseMapper.class)
public interface IAircraftMapper {

    IAircraftMapper INSTANCE = Mappers.getMapper(IAircraftMapper.class);

    @Mapping(target = "daytimeConfiguration", source = "entity", qualifiedByName = "seatingToDayTimeConfig")
    @Mapping(target = "nighttimeConfiguration", source = "entity", qualifiedByName = "mapNightTimeConfig")
    @Mapping(target = "bases", qualifiedByName = "toAircraftBaseSimpleDtoList")
    @Mapping(target = "aircraftType.category.subcategories", ignore = true)
    @Mapping(target = "aircraftType.category.parentCategory", ignore = true)
    @Mapping(target = "aircraftType.subcategory.parentCategory", ignore = true)
    @Mapping(target = "aircraftType.subcategory.subcategories", ignore = true)
    @Mapping(target = "contributionAircrafts", ignore = true)
    AircraftDto toDto(final Aircraft entity);

    @Mapping(target = "bases", ignore = true)
    @Mapping(target = "observations", ignore = true)
    @Mapping(target = "operator", ignore = true)
    @Mapping(target = "aircraftType", ignore = true)
    @Mapping(target = "contributionAircrafts", ignore = true)
    Aircraft toEntity(final AircraftDto aircraftDto);

    @Mapping(target = "bases", ignore = true)
    @Mapping(target = "observations", ignore = true)
    @Mapping(target = "operator", ignore = true)
    @Mapping(target = "aircraftType", ignore = true)
    @Mapping(target = "contributionAircrafts", ignore = true)
    void updateFromDto(final AircraftDto aircraftDto, @MappingTarget Aircraft aircraft);

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

    @Named("toAircraftBaseSimpleDtoList")
    @IterableMapping(qualifiedByName = "toAircraftBaseSimpleDto")
    List<AircraftBaseDto> toAircraftBaseSimpleDtoList(final List<AircraftBase> entityList);
}
