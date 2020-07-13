package com.europair.management.rest.tasks.service;

import com.europair.management.rest.model.tasks.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

  Page<TaskDTO> findAllPaginated(Pageable pageable);
  TaskDTO findById(Long id);
  TaskDTO saveTask(TaskDTO taskDTO);
  TaskDTO updateTask(Long id, TaskDTO roleDTO);
  void deleteTask(Long id);

}
