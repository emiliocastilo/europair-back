package com.europair.management.rest.model.countries.mapper;

import com.europair.management.api.dto.countries.dto.CountryDTO;
import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
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
