package com.europair.management.impl.mappers.fleet;

import com.europair.management.api.dto.fleet.AircraftObservationDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.AircraftObservation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IAircraftObservationMapper {

    IAircraftObservationMapper INSTANCE = Mappers.getMapper(IAircraftObservationMapper.class);

    AircraftObservationDto toDto(final AircraftObservation entity);

    AircraftObservation toEntity(final AircraftObservationDto dto);

    void updateFromDto(final AircraftObservationDto dto, @MappingTarget AircraftObservation entity);

}
