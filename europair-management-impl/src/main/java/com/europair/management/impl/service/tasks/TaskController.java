package com.europair.management.impl.service.tasks;

import com.europair.management.api.dto.tasks.TaskDTO;
import com.europair.management.api.service.tasks.ITaskController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController implements ITaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private final ITaskService taskService;

    public ResponseEntity<Page<TaskDTO>> getAllSTasksPaginated(final Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[TaskController] - Starting method [getAllSTasksPaginated] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<TaskDTO> pageTaskssDTO = taskService.findAllPaginated(pageable, criteria);
        LOGGER.debug("[TaskController] - Ending method [getAllSTasksPaginated] with return: {}", pageTaskssDTO);
        return ResponseEntity.ok().body(pageTaskssDTO);
    }

    public ResponseEntity<TaskDTO> getTaskById(final Long id) {
        LOGGER.debug("[TaskController] - Starting method [getTaskById] with input: id={}", id);
        final TaskDTO taskDTO = taskService.findById(id);
        LOGGER.debug("[TaskController] - Ending method [getTaskById] with return: {}", taskDTO);
        return ResponseEntity.ok().body(taskDTO);
    }

    public ResponseEntity<TaskDTO> saveTask(final TaskDTO taskDTO) {
        LOGGER.debug("[TaskController] - Starting method [saveTask] with input: taskDTO={}", taskDTO);
        final TaskDTO taskDTOSaved = taskService.saveTask(taskDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskDTOSaved.getId())
                .toUri();
        LOGGER.debug("[TaskController] - Ending method [saveTask] with return: {}", taskDTOSaved);
        return ResponseEntity.created(location).body(taskDTOSaved);
    }

    public ResponseEntity<TaskDTO> updateTask(final Long id, final TaskDTO taskDTO) {
        LOGGER.debug("[TaskController] - Starting method [updateTask] with input: id={}, taskDTO={}", id, taskDTO);
        final TaskDTO taskDTOSaved = taskService.updateTask(id, taskDTO);
        LOGGER.debug("[TaskController] - Ending method [updateTask] with return: {}", taskDTOSaved);
        return ResponseEntity.ok().body(taskDTOSaved);
    }

    public ResponseEntity<?> deleteTask(final Long id) {
        LOGGER.debug("[TaskController] - Starting method [deleteTask] with input: id={}", id);
        taskService.deleteTask(id);
        LOGGER.debug("[TaskController] - Ending method [deleteTask] with no return.");
        return ResponseEntity.noContent().build();
    }

}
