package com.europair.management.impl.integrations.office365.mappers;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.api.integrations.office365.dto.ConfirmedOperationDto;
import com.europair.management.impl.mappers.airport.IRunwayMapper;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.impl.mappers.operatorsAirports.IOperatorsAirportsMapper;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.regions.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

//@Mapper(config = AuditModificationBaseMapperConfig.class,
//        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IConfirmedOperationMapper {
/*
    IConfirmedOperationMapper INSTANCE = Mappers.getMapper(IConfirmedOperationMapper.class);

    @Mapping(target = "city.country", ignore = true)
    @Mapping(target = "elevation.value", source = "elevation")
    @Mapping(target = "elevation.type", source = "elevationUnit")
    @Mapping(target = "runways", qualifiedByName = "toRunwayDto")
    @Mapping(target = "operators", qualifiedByName = "operatorsAirportToDtoFromAirport")
    @Mapping(target = "regions", qualifiedByName = "regionToSimpleDto")
    ConfirmedOperationDto toDto(final  entity);

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
*/
}
