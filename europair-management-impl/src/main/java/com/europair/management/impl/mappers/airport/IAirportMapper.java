package com.europair.management.impl.mappers.airport;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.airport.RunwayDto;
import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.impl.mappers.operatorsAirports.IOperatorsAirportsMapper;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.entity.Runway;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import com.europair.management.rest.model.regions.entity.Region;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = {IRunwayMapper.class, IOperatorsAirportsMapper.class})
public interface IAirportMapper {

    IAirportMapper INSTANCE = Mappers.getMapper(IAirportMapper.class);

    @Mapping(target = "city.country", ignore = true)
    @Mapping(target = "elevation.value", source = "elevation")
    @Mapping(target = "elevation.type", source = "elevationUnit")
    @Mapping(target = "runways", qualifiedByName = "toRunwayDtoList")
    @Mapping(target = "operators", qualifiedByName = "operatorsAirportToDtoFromAirportList")
    @Mapping(target = "regions", qualifiedByName = "regionToSimpleDtoList")
    AirportDto toDto(final Airport entity);

    @Mapping(source = "elevation.value", target = "elevation")
    @Mapping(source = "elevation.type", target = "elevationUnit")
    @Mapping(target = "runways", ignore = true)
    @Mapping(target = "terminals", ignore = true)
    @Mapping(target = "observations", ignore = true)
    @Mapping(target = "operators", ignore = true)
    @Mapping(target = "regions", ignore = true)
    Airport toEntity(final AirportDto dto);

    @Mapping(target = "country", source = "airportDto", qualifiedByName = "mapAirportCountryToEntity")
    @Mapping(target = "city", source = "airportDto", qualifiedByName = "mapAirportCityToEntity")
    @Mapping(source = "elevation.value", target = "elevation")
    @Mapping(source = "elevation.type", target = "elevationUnit")
    @Mapping(target = "runways", ignore = true)
    @Mapping(target = "terminals", ignore = true)
    @Mapping(target = "observations", ignore = true)
    @Mapping(target = "operators", ignore = true)
    @Mapping(target = "regions", ignore = true)
    void updateFromDto(final AirportDto airportDto, @MappingTarget Airport airport);

    @Named("mapAirportCountryToEntity")
    default Country mapAirportCountryToEntity(AirportDto dto) {
        Country country = new Country();
        country.setId(dto.getCountry().getId());
        return country;
    }

    @Named("mapAirportCityToEntity")
    default City mapAirportCityToEntity(AirportDto dto) {
        City city = new City();
        city.setId(dto.getCity().getId());
        return city;
    }

    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "airports", ignore = true)
    @Named("regionToSimpleDto")
    RegionDTO regionToSimpleDto(final Region entity);


    @Mapping(target = "country", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "elevation", ignore = true)
    @Mapping(target = "terminals", ignore = true)
    @Mapping(target = "runways", ignore = true)
    @Mapping(target = "observations", ignore = true)
    @Mapping(target = "operators", ignore = true)
    @Mapping(target = "regions", ignore = true)
    @Named("airportToSimpleDto")
    AirportDto toSimpleDto(final Airport entity);

    @Named("toRunwayDtoList")
    @IterableMapping(qualifiedByName = "toRunwayDto")
    List<RunwayDto> toRunwayDtoList(final List<Runway> entityList);

    @Named("operatorsAirportToDtoFromAirportList")
    @IterableMapping(qualifiedByName = "operatorsAirportToDtoFromAirport")
    List<OperatorsAirportsDTO> toDtoFromAirportList(final List<OperatorsAirports> entity);

    @Named("regionToSimpleDtoList")
    @IterableMapping(qualifiedByName = "regionToSimpleDto")
    List<RegionDTO> regionToSimpleDtoList(final List<Region> entity);
}
