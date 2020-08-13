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
@RequestMapping("/tasks")
public class TaskController implements ITaskController {

  private final ITaskService taskService;

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
