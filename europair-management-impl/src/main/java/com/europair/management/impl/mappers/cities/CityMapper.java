package com.europair.management.impl.mappers.cities;

import com.europair.management.api.dto.cities.dto.CityDTO;
import com.europair.management.api.dto.countries.dto.CountryDTO;
import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.countries.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface CityMapper {

  CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

  CityDTO toDto (final City entity);

  CountryDTO toDto (final Country entity);

  List<CityDTO> toListDtos (final List<City> listEntities);

  City toEntity (final CityDTO cityDTO);

  Country toEntity (final CountryDTO countryDTO);

  void updateFromDto(final CityDTO cityDTO, @MappingTarget City city);

}
