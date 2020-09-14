package com.europair.management.impl.mappers.routes;

import com.europair.management.api.dto.routes.RouteFrequencyDayDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.routes.entity.RouteFrequencyDay;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IRouteFrequencyDayMapper {

    IRouteFrequencyDayMapper INSTANCE = Mappers.getMapper(IRouteFrequencyDayMapper.class);

    RouteFrequencyDayDto toDto(final RouteFrequencyDay entity);

    RouteFrequencyDay toEntity(final RouteFrequencyDayDto dto);

}
