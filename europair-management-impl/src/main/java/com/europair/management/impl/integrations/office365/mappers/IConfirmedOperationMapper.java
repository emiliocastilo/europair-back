package com.europair.management.impl.integrations.office365.mappers;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.api.integrations.office365.dto.ConfirmedOperationDto;
import com.europair.management.api.integrations.office365.dto.FlightExtendedInfoDto;
import com.europair.management.api.integrations.office365.dto.FlightSharingInfoDTO;
import com.europair.management.impl.mappers.airport.IRunwayMapper;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.impl.mappers.operatorsAirports.IOperatorsAirportsMapper;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.entity.FlightService;
import com.europair.management.rest.model.regions.entity.Region;
import com.europair.management.rest.model.routes.entity.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IConfirmedOperationMapper {
    IConfirmedOperationMapper INSTANCE = Mappers.getMapper(IConfirmedOperationMapper.class);

//    @Mapping(target = "city.country", ignore = true)
//    @Mapping(target = "operators", qualifiedByName = "operatorsAirportToDtoFromAirport")
//    @Mapping(target = "regions", qualifiedByName = "regionToSimpleDto")

    @Mapping(target = "fileInfo.code", source = "route")
    @Mapping(target = "fileInfo.description", source = "route")
//    @Mapping(target = "fileInfo.fileUrl", source = "route") ToDo: de donde sale esto??

    @Mapping(target = "flightsInfo", source = "route.flights", qualifiedByName = "mapFlight")
    ConfirmedOperationDto convert(Route route, Contribution contribution);

    @Mapping(target = "operationType", source = "route")
    @Mapping(target = "originAirport", source = "route")
    @Mapping(target = "destinationAirport", source = "route")
    @Mapping(target = "startDate", source = "route")
    @Mapping(target = "endDate", source = "route")
    @Mapping(target = "flightNumber", source = "route")
    @Mapping(target = "operator", source = "route")
    @Mapping(target = "plateNumber", source = "route")
    @Mapping(target = "client", source = "route")
    @Mapping(target = "paxTotalNumber", source = "route")
    @Mapping(target = "bedsNumber", source = "beds")
    @Mapping(target = "stretchersNumber", source = "route")
    @Mapping(target = "charge", source = "route")
    @Named("mapFlight")
    FlightSharingInfoDTO mapFlight(Flight flight);

    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "airports", ignore = true)
    @Named("regionToSimpleDto")
    RegionDTO regionToSimpleDto(final Region entity);

}
