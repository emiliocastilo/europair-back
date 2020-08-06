package com.europair.management.rest.model.masters.menu.mapper;

import com.europair.management.api.dto.menu.dto.MenuOptionDto;
import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.masters.menu.entity.MenuOption;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface MenuOptionMapper {

    MenuOptionMapper INSTANCE = Mappers.getMapper(MenuOptionMapper.class);

    MenuOptionDto toDto (final MenuOption entity);

    List<MenuOptionDto> toListDtos (final List<MenuOption> listEntities);

    MenuOption toEntity (final MenuOptionDto menuOptionDto);

}
