package com.europair.management.impl.mappers.flights;

import com.europair.management.api.dto.flights.FlightServiceDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.flights.entity.FlightService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IFlightServiceMapper {

    IFlightServiceMapper INSTANCE = Mappers.getMapper(IFlightServiceMapper.class);

    @Mapping(target = "seller.password", ignore = true)
    @Mapping(target = "seller.roles", ignore = true)
    @Mapping(target = "seller.tasks", ignore = true)
    FlightServiceDto toDto(final FlightService entity);

    FlightService toEntity(final FlightServiceDto dto);

    @Mapping(target = "flightId", ignore = true)
    void updateFromDto(final FlightServiceDto dto, @MappingTarget FlightService entity);

}
