package com.europair.management.rest.screens.mapper;

import com.europair.management.rest.screens.dto.ScreenDTO;
import com.europair.management.rest.screens.entity.Screen;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ScreenMapper {

  ScreenMapper INSTANCE = Mappers.getMapper(ScreenMapper.class);

  ScreenDTO toDto (final Screen entity);

  List<ScreenDTO> toListDtos (final List<Screen> listEntities);

}
