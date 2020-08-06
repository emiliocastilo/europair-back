package com.europair.management.api.service.tasks.controller;

import com.europair.management.api.dto.tasks.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tasks")
public interface ITaskController {



  @GetMapping("")
  public ResponseEntity<Page<TaskDTO>> getAllSTasksPaginated(final Pageable pageable) ;

  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> getTaskById(@PathVariable final Long id) ;

  @PostMapping("")
  public ResponseEntity<TaskDTO> saveTask(@RequestBody final TaskDTO taskDTO) ;

  @PutMapping("/{id}")
  public ResponseEntity<TaskDTO> updateTask(@PathVariable final Long id, @RequestBody final TaskDTO taskDTO) ;

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTask(@PathVariable final Long id) ;

}
