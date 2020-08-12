package com.europair.management.impl.mappers.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = AircraftBaseMapper.class)
public interface AircraftMapper {

    AircraftMapper INSTANCE = Mappers.getMapper(AircraftMapper.class);

    @Mapping(target = "bases", qualifiedByName = "aircraftBaseNoAircraft")
    AircraftDto toDto(final Aircraft entity);

    List<AircraftDto> toDto(final List<Aircraft> entityList);

    @Mapping(target = "bases", qualifiedByName = "aircraftBaseEntityNoAircraft")
    Aircraft toEntity(final AircraftDto aircraftDto);

    void updateFromDto(final AircraftDto aircraftDto, @MappingTarget Aircraft aircraft);
}
