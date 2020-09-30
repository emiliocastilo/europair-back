package com.europair.management.impl.mappers.tasks;

import com.europair.management.api.dto.tasks.TaskDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.tasks.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface TaskMapper {

  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  TaskDTO toDto (final Task entity);

  List<TaskDTO> toListDtos (final List<Task> listEntities);

  Task toEntity (final TaskDTO taskDTO);

  @Mapping(target = "screens", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "users", ignore = true)
  @Mapping(target = "tasksScreens", ignore = true)
  void updateFromDto(final TaskDTO dto, @MappingTarget Task entity);

}
