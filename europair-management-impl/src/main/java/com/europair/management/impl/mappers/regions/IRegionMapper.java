package com.europair.management.impl.mappers.regions;

import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.impl.mappers.airport.IAirportMapper;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.regions.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = {IAirportMapper.class})
public interface IRegionMapper {

  IRegionMapper INSTANCE = Mappers.getMapper(IRegionMapper.class);

  RegionDTO toDto (final Region entity);

  Region toEntity (final RegionDTO regionDTO);

  void updateFromDto(final RegionDTO regionDTO, @MappingTarget Region region);

  @Mapping(target = "countries", ignore = true)
  @Mapping(target = "airports", ignore = true)
  RegionDTO toSimpleDto(final Region entity);
}
