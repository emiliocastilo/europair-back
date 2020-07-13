package com.europair.management.rest.model.screens.mapper;

import com.europair.management.rest.model.screens.dto.ScreenDTO;
import com.europair.management.rest.model.screens.entity.Screen;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ScreenMapper {

  ScreenMapper INSTANCE = Mappers.getMapper(ScreenMapper.class);

  ScreenDTO toDto (final Screen entity);

  List<ScreenDTO> toListDtos (final List<Screen> listEntities);

}
