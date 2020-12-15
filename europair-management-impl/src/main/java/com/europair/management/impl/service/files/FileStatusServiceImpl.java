package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileStatusDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.files.IFileStatusMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.files.entity.FileStatus;
import com.europair.management.rest.model.files.repository.FileStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileStatusServiceImpl implements IFileStatusService {

    @Autowired
    private FileStatusRepository fileStatusRepository;

    @Override
    public FileStatusDto findById(Long id) {
        return IFileStatusMapper.INSTANCE.toDto(fileStatusRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_STATUS_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public Page<FileStatusDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return fileStatusRepository.findFileStatusByCriteria(criteria, pageable)
                .map(IFileStatusMapper.INSTANCE::toDto);
    }

    @Override
    public FileStatusDto saveFileStatus(final FileStatusDto fileStatusDto) {

        if (fileStatusDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_STATUS_NEW_WITH_ID, String.valueOf(fileStatusDto.getId()));
        }
        FileStatus fileStatus = IFileStatusMapper.INSTANCE.toEntity(fileStatusDto);
        fileStatus = fileStatusRepository.save(fileStatus);

        return IFileStatusMapper.INSTANCE.toDto(fileStatus);
    }

    @Override
    public FileStatusDto updateFileStatus(Long id, FileStatusDto fileStatusDto) {
        FileStatus fileStatus = fileStatusRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_STATUS_NOT_FOUND, String.valueOf(id)));

        IFileStatusMapper.INSTANCE.updateFromDto(fileStatusDto, fileStatus);
        fileStatus = fileStatusRepository.save(fileStatus);

        return IFileStatusMapper.INSTANCE.toDto(fileStatus);
    }

    @Override
    public void deleteFileStatus(Long id) {
        if (!fileStatusRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_STATUS_NOT_FOUND, String.valueOf(id));
        }
        fileStatusRepository.deleteById(id);
    }
}
