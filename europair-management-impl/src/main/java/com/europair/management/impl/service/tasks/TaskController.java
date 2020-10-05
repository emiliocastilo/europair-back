package com.europair.management.impl.service.tasks;

import com.europair.management.api.dto.tasks.TaskDTO;
import com.europair.management.api.service.tasks.ITaskController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController implements ITaskController {

  private final ITaskService taskService;

  public ResponseEntity<Page<TaskDTO>> getAllSTasksPaginated(final Pageable pageable, Map<String, String> reqParam) {
    CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
    final Page<TaskDTO> pageTaskssDTO = taskService.findAllPaginated(pageable, criteria);
    return ResponseEntity.ok().body(pageTaskssDTO);
  }

  public ResponseEntity<TaskDTO> getTaskById(final Long id) {
    final TaskDTO taskDTO = taskService.findById(id);
    return ResponseEntity.ok().body(taskDTO);
  }

  public ResponseEntity<TaskDTO> saveTask(final TaskDTO taskDTO) {
    final TaskDTO taskDTOSaved = taskService.saveTask(taskDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(taskDTOSaved.getId())
            .toUri();

    return ResponseEntity.created(location).body(taskDTOSaved);
  }

  public ResponseEntity<TaskDTO> updateTask(final Long id, final TaskDTO taskDTO) {
    final TaskDTO taskDTOSaved = taskService.updateTask(id, taskDTO);
    return ResponseEntity.ok().body(taskDTOSaved);
  }

  public ResponseEntity<?> deleteTask(final Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }

}
