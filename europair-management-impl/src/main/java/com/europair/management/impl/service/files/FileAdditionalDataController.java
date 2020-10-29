package com.europair.management.impl.service.files;


import com.europair.management.api.dto.files.FileAdditionalDataDto;
import com.europair.management.api.service.files.IFileAdditionalDataController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private IFileAdditionalDataService fileAdditionalDataService;

    @Override
    public ResponseEntity<FileAdditionalDataDto> getFileAdditionalDataById(@NotNull Long fileId, @NotNull Long id) {
        FileAdditionalDataDto dto = fileAdditionalDataService.findById(fileId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<FileAdditionalDataDto>> getFileAdditionalDataByFilter(@NotNull Long fileId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<FileAdditionalDataDto> dtoPage = fileAdditionalDataService.findAllPaginatedByFilter(fileId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<FileAdditionalDataDto> saveFileAdditionalData(@NotNull Long fileId, @NotNull @Valid FileAdditionalDataDto fileAdditionalDataDto) {
        final FileAdditionalDataDto dtoSaved = fileAdditionalDataService.saveFileAdditionalData(fileId, fileAdditionalDataDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<FileAdditionalDataDto> updateFileAdditionalData(@NotNull Long fileId, @NotNull Long id,
                                                                          @NotNull @Valid FileAdditionalDataDto fileAdditionalDataDto) {
        fileAdditionalDataService.updateFileAdditionalData(fileId, id, fileAdditionalDataDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteFileAdditionalData(@NotNull Long fileId, @NotNull Long id) {
        fileAdditionalDataService.deleteFileAdditionalData(fileId, id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> createOrUpdateFileAdditionalData(@NotNull Long fileId, @NotNull @Valid FileAdditionalDataDto fileAdditionalDataDto) {
        Pair<Boolean, Long> result = fileAdditionalDataService.createOrUpdateFileAdditionalData(fileId, fileAdditionalDataDto);
        return Boolean.TRUE.equals(result.getFirst()) ? ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fileId, result.getSecond()).toUri()).build()
                : ResponseEntity.noContent().build();
    }
}
