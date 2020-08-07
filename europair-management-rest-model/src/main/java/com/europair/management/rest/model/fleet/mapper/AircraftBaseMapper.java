package com.europair.management.rest.model.fleet.mapper;

import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.dto.AircraftBaseDto;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface AircraftBaseMapper {

    AircraftBaseMapper INSTANCE = Mappers.getMapper(AircraftBaseMapper.class);

    @Named("aircraftBaseNoAircraft")
    @Mapping(target = "aircraft", ignore = true)
    AircraftBaseDto toDtoWithoutAircraft(final AircraftBase entity);

    @Named("aircraftBaseEntityNoAircraft")
    @Mapping(target = "aircraft", ignore = true)
    AircraftBaseDto toEntityWithoutAircraft(final AircraftBaseDto dto);
}
