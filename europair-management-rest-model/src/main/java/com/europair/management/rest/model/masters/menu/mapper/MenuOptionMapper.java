package com.europair.management.rest.model.masters.menu.mapper;

import com.europair.management.rest.model.masters.menu.dto.MenuOptionDto;
import com.europair.management.rest.model.masters.menu.entity.MenuOption;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuOptionMapper {

    MenuOptionMapper INSTANCE = Mappers.getMapper(MenuOptionMapper.class);

    MenuOptionDto toDto (final MenuOption entity);

    List<MenuOptionDto> toListDtos (final List<MenuOption> listEntities);

    MenuOption toEntity (final MenuOptionDto menuOptionDto);

}