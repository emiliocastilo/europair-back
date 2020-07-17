package com.europair.management.rest.model.countries.mapper;

import com.europair.management.rest.model.countries.dto.CountryDTO;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.roles.dto.RoleDTO;
import com.europair.management.rest.model.roles.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CountryMapper {

  CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

  CountryDTO toDto (final Country entity);

  List<CountryDTO> toListDtos (final List<Country> listEntities);

  Country toEntity (final CountryDTO countryDTO);

}
