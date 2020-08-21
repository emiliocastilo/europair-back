package com.europair.management.impl.mappers.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = IAircraftBaseMapper.class)
public interface AircraftMapper {

    AircraftMapper INSTANCE = Mappers.getMapper(AircraftMapper.class);

    @Mapping(target = "daytimeConfiguration", source = "entity", qualifiedByName = "seatingToDayTimeConfig")
    @Mapping(target = "nighttimeConfiguration", source = "entity", qualifiedByName = "mapNightTimeConfig")
    @Mapping(target = "bases", qualifiedByName = "toAircraftBaseSimpleDto")
    AircraftDto toDto(final Aircraft entity);

    @Mapping(target = "bases", ignore = true)
    @Mapping(target = "observations", ignore = true)
    Aircraft toEntity(final AircraftDto aircraftDto);

    @Mapping(target = "bases", ignore = true)
    @Mapping(target = "observations", ignore = true)
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
}
