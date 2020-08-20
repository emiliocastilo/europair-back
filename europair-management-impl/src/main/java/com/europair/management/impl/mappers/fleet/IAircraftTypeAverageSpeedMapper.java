package com.europair.management.impl.mappers.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeAverageSpeedDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.entity.AircraftTypeAverageSpeed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IAircraftTypeAverageSpeedMapper {

    IAircraftTypeAverageSpeedMapper INSTANCE = Mappers.getMapper(IAircraftTypeAverageSpeedMapper.class);

    @Mapping(target = "aircraftType", ignore = true)
    AircraftTypeAverageSpeed toEntity(final AircraftTypeAverageSpeedDto dto);

    AircraftTypeAverageSpeedDto toDto(final AircraftTypeAverageSpeed entity);

    @Mapping(target = "aircraftType", ignore = true)
    void updateFromDto(final AircraftTypeAverageSpeedDto dto, @MappingTarget AircraftTypeAverageSpeed entity);

}
