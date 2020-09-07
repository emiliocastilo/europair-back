package com.europair.management.impl.mappers.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.flights.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class,
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IFlightMapper {

  IFlightMapper INSTANCE = Mappers.getMapper(IFlightMapper.class);

  FlightDTO toDto (final Flight entity);

  List<FlightDTO> toListDtos (final List<Flight> listEntities);

  Flight toEntity (final FlightDTO flightDTO);

  void updateFromDto(final FlightDTO flightDTO, @MappingTarget Flight flight);

}