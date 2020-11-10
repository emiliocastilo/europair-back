package com.europair.management.impl.service.files;


import com.europair.management.api.dto.files.FileAdditionalDataDto;
import com.europair.management.api.service.files.IFileAdditionalDataController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class FileAdditionalDataController implements IFileAdditionalDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileAdditionalDataController.class);

    @Autowired
    private IFileAdditionalDataService fileAdditionalDataService;

    @Override
    public ResponseEntity<FileAdditionalDataDto> getFileAdditionalDataById(@NotNull Long fileId, @NotNull Long id) {
        LOGGER.debug("[FileAdditionalDataController] - Starting method [getFileAdditionalDataById] with input: id={}", id);
        FileAdditionalDataDto dto = fileAdditionalDataService.findById(fileId, id);
        LOGGER.debug("[FileAdditionalDataController] - Ending method [getFileAdditionalDataById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<FileAdditionalDataDto>> getFileAdditionalDataByFilter(@NotNull Long fileId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[FileAdditionalDataController] - Starting method [getFileAdditionalDataByFilter] with input: fileId={}, pageable={}, reqParam={}", fileId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<FileAdditionalDataDto> dtoPage = fileAdditionalDataService.findAllPaginatedByFilter(fileId, pageable, criteria);
        LOGGER.debug("[FileAdditionalDataController] - Ending method [getFileAdditionalDataByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<FileAdditionalDataDto> saveFileAdditionalData(@NotNull Long fileId, @NotNull @Valid FileAdditionalDataDto fileAdditionalDataDto) {
        LOGGER.debug("[FileAdditionalDataController] - Starting method [saveFileAdditionalData] with input: fileId={}, fileAdditionalDataDto={}", fileId, fileAdditionalDataDto);
        final FileAdditionalDataDto dtoSaved = fileAdditionalDataService.saveFileAdditionalData(fileId, fileAdditionalDataDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileId, dtoSaved.getId())
                .toUri();

        LOGGER.debug("[FileAdditionalDataController] - Ending method [saveFileAdditionalData] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<FileAdditionalDataDto> updateFileAdditionalData(@NotNull Long fileId, @NotNull Long id,
                                                                          @NotNull @Valid FileAdditionalDataDto fileAdditionalDataDto) {
        LOGGER.debug("[FileAdditionalDataController] - Starting method [updateFileAdditionalData] with input: fileId={}, id={}, fileAdditionalDataDto={}", fileId, id, fileAdditionalDataDto);
        fileAdditionalDataService.updateFileAdditionalData(fileId, id, fileAdditionalDataDto);
        LOGGER.debug("[FileAdditionalDataController] - Ending method [updateFileAdditionalData] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteFileAdditionalData(@NotNull Long fileId, @NotNull Long id) {
        LOGGER.debug("[FileAdditionalDataController] - Starting method [deleteFileAdditionalData] with input: fileId={}, id={}", fileId, id);
        fileAdditionalDataService.deleteFileAdditionalData(fileId, id);
        LOGGER.debug("[FileAdditionalDataController] - Ending method [deleteFileAdditionalData] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> createOrUpdateFileAdditionalData(@NotNull Long fileId, @NotNull @Valid FileAdditionalDataDto fileAdditionalDataDto) {
        LOGGER.debug("[FileAdditionalDataController] - Starting method [createOrUpdateFileAdditionalData] with input: fileId={}, fileAdditionalDataDto={}", fileId, fileAdditionalDataDto);
        Pair<Boolean, Long> result = fileAdditionalDataService.createOrUpdateFileAdditionalData(fileId, fileAdditionalDataDto);
        LOGGER.debug("[FileAdditionalDataController] - Ending method [createOrUpdateFileAdditionalData] with no return.");
        return Boolean.TRUE.equals(result.getFirst()) ? ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fileId, result.getSecond()).toUri()).build()
                : ResponseEntity.noContent().build();
    }
}
