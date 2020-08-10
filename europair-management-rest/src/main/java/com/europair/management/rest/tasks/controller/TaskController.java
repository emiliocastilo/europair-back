package com.europair.management.rest.tasks.controller;

import com.europair.management.rest.common.utils.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.tasks.dto.TaskDTO;
import com.europair.management.rest.tasks.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;

  @GetMapping("")
  @Operation(description = "Paged result of master task with filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
  public ResponseEntity<Page<TaskDTO>> getAllSTasksPaginated(
          @Parameter(description = "Pagination filter") final Pageable pageable,
          @Parameter(description = "Map of properties to filter with value and operator, (pe: description=test,CONTAINS)") @RequestParam Map<String, String> reqParam) {

    CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
    final Page<TaskDTO> pageTaskssDTO = taskService.findAllPaginated(pageable, criteria);
    return ResponseEntity.ok().body(pageTaskssDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> getTaskById(@PathVariable final Long id) {
    final TaskDTO taskDTO = taskService.findById(id);
    return ResponseEntity.ok().body(taskDTO);
  }

  @PostMapping("")
  public ResponseEntity<TaskDTO> saveTask(@RequestBody final TaskDTO taskDTO) {

    final TaskDTO taskDTOSaved = taskService.saveTask(taskDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(taskDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(taskDTOSaved);

  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskDTO> updateTask(@PathVariable final Long id, @RequestBody final TaskDTO taskDTO) {

    final TaskDTO taskDTOSaved = taskService.updateTask(id, taskDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(taskDTOSaved.getId())
      .toUri();

    return ResponseEntity.ok().body(taskDTOSaved);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTask(@PathVariable final Long id) {

    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();

  }

}
