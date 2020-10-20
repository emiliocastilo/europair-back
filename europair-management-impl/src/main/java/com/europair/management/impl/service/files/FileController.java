package com.europair.management.impl.service.files;

import com.europair.management.api.dto.common.StateChangeDto;
import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.service.files.IFileController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class FileController implements IFileController {

    @Autowired
    private IFileService fileService;

    @Override
    public ResponseEntity<FileDTO> getFileById(@NotNull final Long id) {
        final FileDTO fileDTO = fileService.findById(id);
        return ResponseEntity.ok(fileDTO);
    }

    @Override
    public ResponseEntity<Page<FileDTO>> getFilesByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<FileDTO> fileDTOPage = fileService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(fileDTOPage);
    }

    @Override
    public ResponseEntity<FileDTO> saveFile(@NotNull final FileDTO fileDTO) {

        final FileDTO fileDTOSaved = fileService.saveFile(fileDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileDTOSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(fileDTOSaved);

    }

    @Override
    public void updateFile(@NotNull final Long id, @NotNull final FileDTO fileDTO) {
        fileService.updateFile(id, fileDTO);
    }

    @Override
    public void deleteFile(@NotNull final Long id) {
        fileService.deleteFile(id);
    }

    @Override
    public ResponseEntity<?> changeState(@NotNull @Valid StateChangeDto<FileStatesEnum> stateChangeDto) {
        fileService.updateStates(stateChangeDto.getIdList(), stateChangeDto.getState());
        return ResponseEntity.noContent().build();
    }
}
