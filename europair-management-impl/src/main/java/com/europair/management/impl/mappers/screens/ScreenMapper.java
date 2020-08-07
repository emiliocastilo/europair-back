package com.europair.management.impl.mappers.screens;

import com.europair.management.api.dto.screens.dto.ScreenDTO;
import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.screens.entity.Screen;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface ScreenMapper {

  ScreenMapper INSTANCE = Mappers.getMapper(ScreenMapper.class);

  ScreenDTO toDto (final Screen entity);

  List<ScreenDTO> toListDtos (final List<Screen> listEntities);

}
