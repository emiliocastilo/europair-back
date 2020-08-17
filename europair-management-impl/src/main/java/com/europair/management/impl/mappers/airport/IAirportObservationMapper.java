package com.europair.management.impl.mappers.airport;

import com.europair.management.api.dto.airport.AirportObservationDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.airport.entity.AirportObservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IAirportObservationMapper {

    IAirportObservationMapper INSTANCE = Mappers.getMapper(IAirportObservationMapper.class);

    //    @Mapping(target = "airport", ignore = true)
    AirportObservationDto toDto(final AirportObservation entity);

    AirportObservation toEntity(final AirportObservationDto dto);

    @Mapping(target = "airport", ignore = true)
    void updateFromDto(final AirportObservationDto dto, @MappingTarget AirportObservation entity);

}
