package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileStatusDto;
import com.europair.management.api.service.files.IFileStatusController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private IFileStatusService fileStatusService;

    public ResponseEntity<FileStatusDto> getFileStatusById(@NotNull final Long id) {
        final FileStatusDto fileStatusDto = fileStatusService.findById(id);
        return ResponseEntity.ok(fileStatusDto);
    }

    public ResponseEntity<Page<FileStatusDto>> getFileStatusByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<FileStatusDto> fileStatusDtoPage = fileStatusService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(fileStatusDtoPage);
    }

    public ResponseEntity<FileStatusDto> saveFileStatus(@NotNull final FileStatusDto fileStatusDto) {

        final FileStatusDto fileStatusDtoSaved = fileStatusService.saveFileStatus(fileStatusDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileStatusDtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(fileStatusDtoSaved);

    }

    public ResponseEntity<FileStatusDto> updateFileStatus(@NotNull final Long id, @NotNull final FileStatusDto fileStatusDto) {

        final FileStatusDto fileStatusDtoSaved = fileStatusService.updateFileStatus(id, fileStatusDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileStatusDtoSaved.getId())
                .toUri();

        return ResponseEntity.ok().body(fileStatusDtoSaved);

    }

    public ResponseEntity<?> deleteFileStatus(@NotNull final Long id) {

        fileStatusService.deleteFileStatus(id);
        return ResponseEntity.noContent().build();

    }

}
