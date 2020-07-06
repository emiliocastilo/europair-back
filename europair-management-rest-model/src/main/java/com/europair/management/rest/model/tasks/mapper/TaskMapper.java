package com.europair.management.rest.model.tasks.mapper;

import com.europair.management.rest.model.tasks.dto.TaskDTO;
import com.europair.management.rest.model.tasks.entity.Task;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface TaskMapper {

  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  TaskDTO toDto (final Task entity);

  List<TaskDTO> toListDtos (final List<Task> listEntities);

  Task toEntity (final TaskDTO taskDTO);

}
