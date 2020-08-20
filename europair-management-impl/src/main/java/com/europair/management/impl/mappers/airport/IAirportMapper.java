package com.europair.management.impl.mappers.airport;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.countries.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IAirportMapper {

    IAirportMapper INSTANCE = Mappers.getMapper(IAirportMapper.class);

    @Mapping(target = "city.country", ignore = true)
    @Mapping(target = "elevation.value", source = "elevation")
    @Mapping(target = "elevation.type", source = "elevationUnit")
    AirportDto toDto(final Airport entity);

    @Mapping(source = "elevation.value", target = "elevation")
    @Mapping(source = "elevation.type", target = "elevationUnit")
    Airport toEntity(final AirportDto dto);

    @Mapping(target = "country", source = "airportDto", qualifiedByName = "mapAirportCountryToEntity")
    @Mapping(target = "city", source = "airportDto", qualifiedByName = "mapAirportCityToEntity")
    @Mapping(source = "elevation.value", target = "elevation")
    @Mapping(source = "elevation.type", target = "elevationUnit")
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

}
