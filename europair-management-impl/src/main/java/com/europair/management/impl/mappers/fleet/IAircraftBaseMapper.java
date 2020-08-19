package com.europair.management.impl.mappers.fleet;

import com.europair.management.api.dto.fleet.AircraftBaseDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IAircraftBaseMapper {

    IAircraftBaseMapper INSTANCE = Mappers.getMapper(IAircraftBaseMapper.class);

    @Mapping(target = "airport", ignore = true)
    AircraftBase toEntity(final AircraftBaseDto dto);

    @Mapping(target = "airport.city.country", ignore = true)
    @Mapping(target = "airport.elevation", ignore = true)
    AircraftBaseDto toDto(final AircraftBase entity);

    @Mapping(target = "airport", ignore = true)
    void updateFromDto(final AircraftBaseDto dto, @MappingTarget AircraftBase entity);

    @Named("toAircraftBaseSimpleDto")
    @Mapping(target = "airport.country", ignore = true)
    @Mapping(target = "airport.city", ignore = true)
    @Mapping(target = "airport.elevation", ignore = true)
    AircraftBaseDto toSimpleDto(final AircraftBase entity);
}
