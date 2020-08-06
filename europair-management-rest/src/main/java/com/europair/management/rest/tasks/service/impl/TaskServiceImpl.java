package com.europair.management.rest.tasks.service.impl;

import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.tasks.dto.TaskDTO;
import com.europair.management.rest.model.tasks.entity.Task;
import com.europair.management.rest.model.tasks.mapper.TaskMapper;
import com.europair.management.rest.tasks.repository.TaskRepository;
import com.europair.management.rest.tasks.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  @Override
  public Page<TaskDTO> findAllPaginated(Pageable pageable) {
    return taskRepository.findAll(pageable).map(screen -> TaskMapper.INSTANCE.toDto(screen));
  }

  @Override
  public TaskDTO findById(Long id) throws ResourceNotFoundException {
    return TaskMapper.INSTANCE.toDto(taskRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Task not found on id: " + id)));
  }

  @Override
  public TaskDTO saveTask(final TaskDTO taskDTO) {
    Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
    task = taskRepository.save(task);
    return TaskMapper.INSTANCE.toDto(task);
  }

  @Override
  public TaskDTO updateTask(final Long id, final TaskDTO taskDTO) {

    Task taskBD = taskRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Task not found on id: " + id));

    TaskDTO taskDTO2Update = updateTaskValues(taskDTO);

    Task task = TaskMapper.INSTANCE.toEntity(taskDTO2Update);
    task = taskRepository.save(task);

    return TaskMapper.INSTANCE.toDto(task);
  }

  @Override
  public void deleteTask(Long id) {

    Task roleBD = taskRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Task not found on id: " + id));
    taskRepository.deleteById(id);
  }

  private TaskDTO updateTaskValues(TaskDTO taskDTO) {

    return TaskDTO.builder()
      .id(taskDTO.getId())
      .name(taskDTO.getName())
      .description(taskDTO.getDescription())
      .screens(taskDTO.getScreens())
      .build();

  }


}
