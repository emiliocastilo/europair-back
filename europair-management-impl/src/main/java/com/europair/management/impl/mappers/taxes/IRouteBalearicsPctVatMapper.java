package com.europair.management.impl.mappers.taxes;

import com.europair.management.api.dto.taxes.RouteBalearicsPctVatDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.taxes.entity.RouteBalearicsPctVat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class,
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IRouteBalearicsPctVatMapper {

  IRouteBalearicsPctVatMapper INSTANCE = Mappers.getMapper(IRouteBalearicsPctVatMapper.class);

  @Mapping(target = "originAirport", ignore = true)
  @Mapping(target = "destinationAirport", ignore = true)
  RouteBalearicsPctVatDTO toDto (final RouteBalearicsPctVat entity);

  List<RouteBalearicsPctVatDTO> toListDtos (final List<RouteBalearicsPctVat> listEntities);

}
