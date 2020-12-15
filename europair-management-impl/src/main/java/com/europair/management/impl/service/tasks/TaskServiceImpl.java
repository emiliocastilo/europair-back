package com.europair.management.impl.service.tasks;


import com.europair.management.api.dto.screens.ScreenDTO;
import com.europair.management.api.dto.tasks.TaskDTO;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.tasks.TaskMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.screens.entity.Screen;
import com.europair.management.rest.model.screens.repository.ScreenRepository;
import com.europair.management.rest.model.tasks.entity.Task;
import com.europair.management.rest.model.tasks.repository.TaskRepository;
import com.europair.management.rest.model.tasksscreens.entity.TasksScreens;
import com.europair.management.rest.model.tasksscreens.entity.TasksScreensPK;
import com.europair.management.rest.model.tasksscreens.repository.TasksScreensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

  private final TaskRepository taskRepository;
  private final ScreenRepository screenRepository;
  private final TasksScreensRepository tasksScreensRepository;

  @Override
  public Page<TaskDTO> findAllPaginated(Pageable pageable, CoreCriteria criteria) {
    return taskRepository.findTasksByCriteria(criteria, pageable).map(TaskMapper.INSTANCE::toDto);
  }

  @Override
  public TaskDTO findById(Long id) {
    return TaskMapper.INSTANCE.toDto(taskRepository.findById(id)
      .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.TASK_NOT_FOUND, String.valueOf(id))));
  }

  @Override
  public TaskDTO saveTask(final TaskDTO taskDTO) {
    Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
    task = taskRepository.save(task);
    return TaskMapper.INSTANCE.toDto(task);
  }

  @Override
  public TaskDTO updateTask(final Long id, final TaskDTO taskDTO) {
    taskDTO.setId(id);
    Task taskBD = taskRepository.findById(id)
      .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.TASK_NOT_FOUND, String.valueOf(id)));

    TaskMapper.INSTANCE.updateFromDto(taskDTO, taskBD);
    taskRepository.save(taskBD);

    /*
      must iterate over the dto screens list adding all the screens that are not in the screens JPA list
       */
    for (ScreenDTO screenDTO : taskDTO.getScreens()){
      if (!existIdScreenDTOInJPAList(screenDTO, taskBD.getTasksScreens())){

        // we have left hand side of the relationship task but we must have both
        Screen screen = screenRepository.findById(screenDTO.getId()).orElseThrow(() ->
                Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.SCREEN_NOT_FOUND, String.valueOf(screenDTO.getId())));

        TasksScreensPK tasksScreensPK = new TasksScreensPK();
        tasksScreensPK.setTaskId(taskBD.getId());
        tasksScreensPK.setScreenId(screen.getId());
        TasksScreens tasksScreens = new TasksScreens();
        tasksScreens.setId(tasksScreensPK);
        tasksScreens.setTask(taskBD);
        tasksScreens.setScreen(screen);

        tasksScreensRepository.save(tasksScreens);
      }
    }
    /*
      must iterate over the jpa list deleting all the task that are not in the DTO list
       */
    for ( TasksScreens tasksScreens : taskBD.getTasksScreens()){
      if (!existIdScreenInDTOList(tasksScreens, taskDTO.getScreens())){
        tasksScreensRepository.delete(tasksScreens);
      }
    }

    taskBD = taskRepository.findById(id)
            .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.TASK_NOT_FOUND, String.valueOf(id)));

    return TaskMapper.INSTANCE.toDto(taskBD);
  }

  private boolean existIdScreenInDTOList(TasksScreens taskScreen, List<ScreenDTO> screenDTOS) {
    boolean res = false;
    for (ScreenDTO screenDTO : screenDTOS){
      if (taskScreen.getId().getScreenId().equals(screenDTO.getId())){
        res = true;
        break;
      }
    }
    return res;
  }

  private boolean existIdScreenDTOInJPAList(ScreenDTO screenDTO, List<TasksScreens> tasksScreens) {
    boolean res = false;
    for (TasksScreens taskScreen : tasksScreens) {
      if (screenDTO.getId().equals(taskScreen.getId().getScreenId())) {
        res = true;
        break;
      }
    }
    return res;
  }

  @Override
  public void deleteTask(Long id) {

    Task roleBD = taskRepository.findById(id)
      .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.TASK_NOT_FOUND, String.valueOf(id)));
    taskRepository.deleteById(id);
  }



}
