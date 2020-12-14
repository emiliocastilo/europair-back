package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileAdditionalDataDto;
import com.europair.management.api.util.ErrorCodesEnum;
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
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_ADDITIONAL_DATA_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public FileAdditionalDataDto saveFileAdditionalData(final Long fileId, FileAdditionalDataDto fileAdditionalDataDto) {
        checkIfFileExists(fileId);
        if (fileAdditionalDataDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_ADDITIONAL_DATA_NEW_WITH_ID, String.valueOf(fileAdditionalDataDto.getId()));
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
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_ADDITIONAL_DATA_NOT_FOUND, String.valueOf(id)));
        IFileAdditionalDataMapper.INSTANCE.updateFromDto(fileAdditionalDataDto, fileAdditionalData);
        fileAdditionalData = fileAdditionalDataRepository.save(fileAdditionalData);
    }

    @Override
    public void deleteFileAdditionalData(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        if (!fileAdditionalDataRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_ADDITIONAL_DATA_NOT_FOUND, String.valueOf(id));
        }
        fileAdditionalDataRepository.deleteById(id);
    }

    @Override
    public Pair<Boolean, Long> createOrUpdateFileAdditionalData(Long fileId, FileAdditionalDataDto fileAdditionalDataDto) {
        checkIfFileExists(fileId);
        FileAdditionalData fileAdditionalData = fileAdditionalDataRepository.findByFileId(fileId).stream()
                .findAny().orElse(null);
        boolean created = fileAdditionalData == null;

        if (fileAdditionalData == null) {
            // Create
            fileAdditionalData = IFileAdditionalDataMapper.INSTANCE.toEntity(fileAdditionalDataDto);
            fileAdditionalData.setFileId(fileId);

        } else {
            // Update
            IFileAdditionalDataMapper.INSTANCE.updateFromDto(fileAdditionalDataDto, fileAdditionalData);
        }

        fileAdditionalData = fileAdditionalDataRepository.save(fileAdditionalData);
        return Pair.of(created, fileAdditionalData.getId());
    }

    private void checkIfFileExists(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NOT_FOUND, String.valueOf(fileId));
        }
    }

}
