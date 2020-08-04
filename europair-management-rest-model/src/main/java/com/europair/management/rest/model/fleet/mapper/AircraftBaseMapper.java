package com.europair.management.rest.model.fleet.mapper;

import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.fleet.dto.AircraftBaseDto;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface AircraftBaseMapper {

    AircraftBaseMapper INSTANCE = Mappers.getMapper(AircraftBaseMapper.class);

    @Mapping(target = "aircraft", ignore = true)
    AircraftBaseDto toDto(final AircraftBase entity);

    @Named("aircraftBaseDtoListNoAircraft")
    default List<AircraftBaseDto> toDto(final List<AircraftBase> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .peek(dto -> dto.setAircraft(null))
                .collect(Collectors.toList());
    }
}
