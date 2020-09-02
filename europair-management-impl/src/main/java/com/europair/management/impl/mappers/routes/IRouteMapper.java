package com.europair.management.impl.mappers.routes;

import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.routes.entity.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IRouteMapper {

    IRouteMapper INSTANCE = Mappers.getMapper(IRouteMapper.class);

    RouteDto toDto(final Route entity);

    @Mapping(target = "rotations", ignore = true)
    @Mapping(target = "file", ignore = true)
    Route toEntity(final RouteDto dto);

    @Mapping(target = "rotations", ignore = true)
    @Mapping(target = "file", ignore = true)
    void updateFromDto(final RouteDto routeDto, @MappingTarget Route route);

/*
    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "routes", ignore = true)
    @Named("regionToSimpleDto")
    RegionDTO regionToSimpleDto(final Region entity);
*/
}
