package com.europair.management.impl.service.files;

import com.europair.management.api.dto.common.StateChangeDto;
import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.service.files.IFileController;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FileController implements IFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IFileService fileService;

    @Override
    public ResponseEntity<FileDTO> getFileById(@NotNull final Long id) {
        LOGGER.debug("[FileController] - Starting method [getFileById] with input: id={}", id);
        final FileDTO fileDTO = fileService.findById(id);
        LOGGER.debug("[FileController] - Ending method [getFileById] with return: {}", fileDTO);
        return ResponseEntity.ok(fileDTO);
    }

    @Override
    public ResponseEntity<Page<FileDTO>> getFilesByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[FileController] - Starting method [getFilesByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<FileDTO> fileDTOPage = fileService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[FileController] - Ending method [getFilesByFilter] with return: {}", fileDTOPage);
        return ResponseEntity.ok(fileDTOPage);
    }

    @Override
    public ResponseEntity<FileDTO> saveFile(@NotNull final FileDTO fileDTO) {
        LOGGER.debug("[FileController] - Starting method [saveFile] with input: fileDTO={}", fileDTO);

        final FileDTO fileDTOSaved = fileService.saveFile(fileDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileDTOSaved.getId())
                .toUri();

        LOGGER.debug("[FileController] - Ending method [saveFile] with return: {}", fileDTOSaved);
        return ResponseEntity.created(location).body(fileDTOSaved);
    }

    @Override
    public void updateFile(@NotNull final Long id, @NotNull final FileDTO fileDTO) {
        LOGGER.debug("[FileController] - Starting method [updateFile] with input: id={}, fileDTO={}", id, fileDTO);
        fileService.updateFile(id, fileDTO);
        LOGGER.debug("[FileController] - Ending method [updateFile] with no return.");
    }

    @Override
    public void deleteFile(@NotNull final Long id) {
        LOGGER.debug("[FileController] - Starting method [deleteFile] with input: id={}", id);
        fileService.deleteFile(id);
        LOGGER.debug("[FileController] - Ending method [deleteFile] with no return.");
    }

    @Override
    public ResponseEntity<?> changeState(@NotNull @Valid StateChangeDto<FileStatesEnum> stateChangeDto) {
        LOGGER.debug("[FileController] - Starting method [changeState] with input: stateChangeDto={}", stateChangeDto);
        fileService.updateStates(stateChangeDto.getIdList(), stateChangeDto.getState());
        LOGGER.debug("[FileController] - Ending method [changeState] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<String>> getValidFileStatesToChange(@NotNull Long id) {
        LOGGER.debug("[FileController] - Starting method [deleteFile] with input: id={}", id);
        List<String> res = fileService.getValidFileStatesToChange(id);
        LOGGER.debug("[FileController] - Ending method [deleteFile] with return: {}", res);
        return ResponseEntity.ok(res);
    }
}
