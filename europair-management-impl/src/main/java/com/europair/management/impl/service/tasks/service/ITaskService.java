package com.europair.management.impl.service.tasks.service;


import com.europair.management.api.dto.tasks.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITaskService {

  Page<TaskDTO> findAllPaginated(Pageable pageable);
  TaskDTO findById(Long id);
  TaskDTO saveTask(TaskDTO taskDTO);
  TaskDTO updateTask(Long id, TaskDTO roleDTO);
  void deleteTask(Long id);

}
