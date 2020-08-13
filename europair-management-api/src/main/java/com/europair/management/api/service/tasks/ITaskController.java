package com.europair.management.api.service.tasks;

import com.europair.management.api.dto.tasks.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/tasks")
public interface ITaskController {



  @GetMapping("")
  @Operation(description = "Paged result of master task with filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
  public ResponseEntity<Page<TaskDTO>> getAllSTasksPaginated(
          @Parameter(description = "Pagination filter") final Pageable pageable,
          @Parameter(description = "Map of properties to filter with value and operator, (pe: description=test,CONTAINS)") @RequestParam Map<String, String> reqParam) ;

  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> getTaskById(@PathVariable final Long id) ;

  @PostMapping("")
  public ResponseEntity<TaskDTO> saveTask(@RequestBody final TaskDTO taskDTO) ;

  @PutMapping("/{id}")
  public ResponseEntity<TaskDTO> updateTask(@PathVariable final Long id, @RequestBody final TaskDTO taskDTO) ;

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTask(@PathVariable final Long id) ;

}
