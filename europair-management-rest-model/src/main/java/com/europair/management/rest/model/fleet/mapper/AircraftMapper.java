package com.europair.management.rest.model.fleet.mapper;

import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.dto.AircraftDto;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = AircraftBaseMapper.class)
public interface AircraftMapper {

    AircraftMapper INSTANCE = Mappers.getMapper(AircraftMapper.class);

    @Mapping(target = "bases", source = "bases", qualifiedByName = "aircraftBaseDtoListNoAircraft")
    AircraftDto toDto(final Aircraft entity);

    List<AircraftDto> toDto(final List<Aircraft> entityList);
}
