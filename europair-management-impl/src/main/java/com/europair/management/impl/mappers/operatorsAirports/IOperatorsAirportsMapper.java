package com.europair.management.impl.mappers.operatorsAirports;

import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IOperatorsAirportsMapper {

  IOperatorsAirportsMapper INSTANCE = Mappers.getMapper(IOperatorsAirportsMapper.class);

  @Mapping(target = "airport.elevation", ignore = true)
  OperatorsAirportsDTO toDto (final OperatorsAirports entity);

  @Mapping(target = "airport.elevation", ignore = true)
  OperatorsAirports toEntity (final OperatorsAirportsDTO operatorsAirportsDTO);

  @Mapping(target = "airport.elevation", ignore = true)
  void updateFromDto(final OperatorsAirportsDTO operatorsAirportsDTO, @MappingTarget OperatorsAirports operatorsAirports);

}
