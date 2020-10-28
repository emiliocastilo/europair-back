package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileAdditionalDataDto;
import com.europair.management.impl.mappers.files.IFileAdditionalDataMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.files.entity.FileAdditionalData;
import com.europair.management.rest.model.files.repository.FileAdditionalDataRepository;
import com.europair.management.rest.model.files.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class FileAdditionalDataServiceImpl implements IFileAdditionalDataService {

    private final String FILE_ID_FILTER = "fileId";

    @Autowired
    private FileAdditionalDataRepository fileAdditionalDataRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Page<FileAdditionalDataDto> findAllPaginatedByFilter(final Long fileId, Pageable pageable, CoreCriteria criteria) {
        checkIfFileExists(fileId);
        Utils.addCriteriaIfNotExists(criteria, FILE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(fileId));

        return fileAdditionalDataRepository.findFileAdditionalDataByCriteria(criteria, pageable)
                .map(IFileAdditionalDataMapper.INSTANCE::toDto);
    }

    @Override
    public FileAdditionalDataDto findById(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        return IFileAdditionalDataMapper.INSTANCE.toDto(fileAdditionalDataRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FileAdditionalData not found with id: " + id)));
    }

    @Override
    public FileAdditionalDataDto saveFileAdditionalData(final Long fileId, FileAdditionalDataDto fileAdditionalDataDto) {
        checkIfFileExists(fileId);
        if (fileAdditionalDataDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New FileAdditionalData expected. Identifier %s got", fileAdditionalDataDto.getId()));
        }

        FileAdditionalData fileAdditionalData = IFileAdditionalDataMapper.INSTANCE.toEntity(fileAdditionalDataDto);
        fileAdditionalData.setFileId(fileId);

        fileAdditionalData = fileAdditionalDataRepository.save(fileAdditionalData);

        return IFileAdditionalDataMapper.INSTANCE.toDto(fileAdditionalData);
    }

    @Override
    public void updateFileAdditionalData(final Long fileId, Long id, FileAdditionalDataDto fileAdditionalDataDto) {
        checkIfFileExists(fileId);
        FileAdditionalData fileAdditionalData = fileAdditionalDataRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FileAdditionalData not found with id: " + id));
        IFileAdditionalDataMapper.INSTANCE.updateFromDto(fileAdditionalDataDto, fileAdditionalData);
        fileAdditionalData = fileAdditionalDataRepository.save(fileAdditionalData);
    }

    @Override
    public void deleteFileAdditionalData(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        if (!fileAdditionalDataRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FileAdditionalData not found with id: " + id);
        }
        fileAdditionalDataRepository.deleteById(id);
    }

    private void checkIfFileExists(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
    }

}
