package com.europair.management.rest.tasks.controller;

import com.europair.management.rest.model.tasks.dto.TaskDTO;
import com.europair.management.rest.tasks.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;

  @GetMapping("")
  public ResponseEntity<Page<TaskDTO>> getAllSTasksPaginated(final Pageable pageable) {

    final Page<TaskDTO> pageTaskssDTO = taskService.findAllPaginated(pageable);
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

}
