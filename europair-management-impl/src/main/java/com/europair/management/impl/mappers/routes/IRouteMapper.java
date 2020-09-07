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

    //    @Mapping(target = "rotations", qualifiedByName = "toDtoNoParent")
    RouteDto toDto(final Route entity);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "parentRoute", ignore = true)
    @Mapping(target = "rotations", ignore = true)
    @Mapping(target = "flights", ignore = true)
    @Mapping(target = "rotationsNumber", source = "rotationsNumber", defaultValue = "1")
    @Mapping(target = "frequency", source = "frequency", defaultValue = "ADHOC")
    Route toEntity(final RouteDto dto);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "parentRoute", ignore = true)
    @Mapping(target = "rotations", ignore = true)
    @Mapping(target = "flights", ignore = true)
    @Mapping(target = "rotationsNumber", source = "rotationsNumber", defaultValue = "1")
    @Mapping(target = "frequency", source = "frequency", defaultValue = "ADHOC")
    void updateFromDto(final RouteDto routeDto, @MappingTarget Route route);

    // Rotations

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "parentRoute", ignore = true)
    @Mapping(target = "rotations", ignore = true)
    @Mapping(target = "flights", ignore = true)
    @Mapping(target = "rotationsNumber", ignore = true)
    @Mapping(target = "frequency", source = "frequency", defaultValue = "ADHOC")
    Route mapRotation(final Route parentRoute);

//    @Named("toDtoNoParent")
//    @Mapping(target = "parentRoute", ignore = true)
//    RouteDto toDtoNoParent(final Route entity);

}