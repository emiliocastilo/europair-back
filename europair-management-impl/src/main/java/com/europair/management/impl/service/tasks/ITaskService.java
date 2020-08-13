package com.europair.management.impl.service.tasks;


import com.europair.management.api.dto.tasks.TaskDTO;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITaskService {

  Page<TaskDTO> findAllPaginated(Pageable pageable, CoreCriteria criteria);
  TaskDTO findById(Long id);
  TaskDTO saveTask(TaskDTO taskDTO);
  TaskDTO updateTask(Long id, TaskDTO roleDTO);
  void deleteTask(Long id);

}
