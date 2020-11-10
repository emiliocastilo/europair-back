package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileStatusDto;
import com.europair.management.api.service.files.IFileStatusController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class FileStatusController implements IFileStatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileStatusController.class);

    @Autowired
    private IFileStatusService fileStatusService;

    public ResponseEntity<FileStatusDto> getFileStatusById(@NotNull final Long id) {
        LOGGER.debug("[FileStatusController] - Starting method [getFileStatusById] with input: id={}", id);
        final FileStatusDto fileStatusDto = fileStatusService.findById(id);
        LOGGER.debug("[FileStatusController] - Ending method [getFileStatusById] with return: {}", fileStatusDto);
        return ResponseEntity.ok(fileStatusDto);
    }

    public ResponseEntity<Page<FileStatusDto>> getFileStatusByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[FileStatusController] - Starting method [getFileStatusByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<FileStatusDto> fileStatusDtoPage = fileStatusService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[FileStatusController] - Ending method [getFileStatusByFilter] with return: {}", fileStatusDtoPage);
        return ResponseEntity.ok(fileStatusDtoPage);
    }

    public ResponseEntity<FileStatusDto> saveFileStatus(@NotNull final FileStatusDto fileStatusDto) {
        LOGGER.debug("[FileStatusController] - Starting method [saveFileStatus] with input: fileStatusDto={}", fileStatusDto);
        final FileStatusDto fileStatusDtoSaved = fileStatusService.saveFileStatus(fileStatusDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileStatusDtoSaved.getId())
                .toUri();

        LOGGER.debug("[FileStatusController] - Ending method [saveFileStatus] with return: {}", fileStatusDtoSaved);
        return ResponseEntity.created(location).body(fileStatusDtoSaved);
    }

    public ResponseEntity<FileStatusDto> updateFileStatus(@NotNull final Long id, @NotNull final FileStatusDto fileStatusDto) {
        LOGGER.debug("[FileStatusController] - Starting method [updateFileStatus] with input: id={}, fileStatusDto={}", id, fileStatusDto);
        final FileStatusDto fileStatusDtoSaved = fileStatusService.updateFileStatus(id, fileStatusDto);
        LOGGER.debug("[FileStatusController] - Ending method [updateFileStatus] with return: {}", fileStatusDtoSaved);
        return ResponseEntity.ok().body(fileStatusDtoSaved);
    }

    public ResponseEntity<?> deleteFileStatus(@NotNull final Long id) {
        LOGGER.debug("[FileStatusController] - Starting method [deleteFileStatus] with input: id={}", id);
        fileStatusService.deleteFileStatus(id);
        LOGGER.debug("[FileStatusController] - Ending method [deleteFileStatus] with no return.");
        return ResponseEntity.noContent().build();

    }

}
