package com.europair.management.impl.mappers.operatorsAirports;

import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IOperatorsAirportsMapper {

  IOperatorsAirportsMapper INSTANCE = Mappers.getMapper(IOperatorsAirportsMapper.class);

  @Mapping(target = "airport.elevation", ignore = true)
  @Mapping(target = "airport.runways", ignore = true)
  @Mapping(target = "airport.terminals", ignore = true)
  @Mapping(target = "airport.observations", ignore = true)
  @Mapping(target = "airport.operators", ignore = true)
  @Mapping(target = "airport.regions", ignore = true)
  OperatorsAirportsDTO toDto (final OperatorsAirports entity);

  @Mapping(target = "airport.elevation", ignore = true)
  @Mapping(target = "airport.runways", ignore = true)
  @Mapping(target = "airport.terminals", ignore = true)
  @Mapping(target = "airport.observations", ignore = true)
  @Mapping(target = "airport.operators", ignore = true)
  @Mapping(target = "airport.regions", ignore = true)
  OperatorsAirports toEntity (final OperatorsAirportsDTO operatorsAirportsDTO);

  @Mapping(target = "airport.elevation", ignore = true)
  @Mapping(target = "airport.runways", ignore = true)
  @Mapping(target = "airport.terminals", ignore = true)
  @Mapping(target = "airport.observations", ignore = true)
  @Mapping(target = "airport.operators", ignore = true)
  @Mapping(target = "airport.regions", ignore = true)
  void updateFromDto(final OperatorsAirportsDTO operatorsAirportsDTO, @MappingTarget OperatorsAirports operatorsAirports);


  // Mappings from Airport side

  //  @Mapping(target = "operator.comments", ignore = true)
  //  @Mapping(target = "operator.operatorsCertifications", ignore = true)
  @Mapping(target = "airport", ignore = true)
  @Named("operatorsAirportToDtoFromAirport")
  OperatorsAirportsDTO toDtoFromAirport(final OperatorsAirports entity);

  @Mapping(target = "operator", ignore = true)
  @Mapping(target = "airport", ignore = true)
  OperatorsAirports toEntityFromAirport(final OperatorsAirportsDTO operatorsAirportsDTO);

  @Mapping(target = "operator", ignore = true)
  @Mapping(target = "airport", ignore = true)
  void updateFromDtoFromAirport(final OperatorsAirportsDTO operatorsAirportsDTO, @MappingTarget OperatorsAirports operatorsAirports);

}
