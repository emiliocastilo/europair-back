package com.europair.management.impl.mappers.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeObservationDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.AircraftTypeObservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IAircraftTypeObservationMapper {

    IAircraftTypeObservationMapper INSTANCE = Mappers.getMapper(IAircraftTypeObservationMapper.class);

    @Mapping(target = "aircraftType", ignore = true)
    AircraftTypeObservation toEntity(final AircraftTypeObservationDto dto);

    AircraftTypeObservationDto toDto(final AircraftTypeObservation entity);

    void updateFromDto(final AircraftTypeObservationDto dto, @MappingTarget AircraftTypeObservation entity);

}
