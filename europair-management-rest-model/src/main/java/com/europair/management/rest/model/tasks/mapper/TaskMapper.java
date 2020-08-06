package com.europair.management.rest.model.tasks.mapper;

import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.api.dto.tasks.dto.TaskDTO;
import com.europair.management.rest.model.tasks.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface TaskMapper {

  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  TaskDTO toDto (final Task entity);

  List<TaskDTO> toListDtos (final List<Task> listEntities);

  Task toEntity (final TaskDTO taskDTO);

}
