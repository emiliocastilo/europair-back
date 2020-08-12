package com.europair.management.impl.mappers.countries;

import com.europair.management.api.dto.countries.CountryDTO;

import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.countries.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface CountryMapper {

  CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

  CountryDTO toDto (final Country entity);

  List<CountryDTO> toListDtos (final List<Country> listEntities);

  Country toEntity (final CountryDTO countryDTO);

}
