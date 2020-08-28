package com.europair.management.impl.service.files;


import com.europair.management.api.dto.files.FileStatusDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.files.IFileStatusMapper;
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
                .orElseThrow(() -> new ResourceNotFoundException("FileStatus not found with id: " + id)));
    }

    @Override
    public Page<FileStatusDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return fileStatusRepository.findFileStatusByCriteria(criteria, pageable)
                .map(IFileStatusMapper.INSTANCE::toDto);
    }

    @Override
    public FileStatusDto saveFileStatus(final FileStatusDto fileStatusDto) {

        if (fileStatusDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New fileStatus expected. Identifier %s got", fileStatusDto.getId()));
        }
        FileStatus fileStatus = IFileStatusMapper.INSTANCE.toEntity(fileStatusDto);
        fileStatus = fileStatusRepository.save(fileStatus);

        return IFileStatusMapper.INSTANCE.toDto(fileStatus);
    }

    @Override
    public FileStatusDto updateFileStatus(Long id, FileStatusDto fileStatusDto) {
        FileStatus fileStatus = fileStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FileStatus not found with id: " + id));

        IFileStatusMapper.INSTANCE.updateFromDto(fileStatusDto, fileStatus);
        fileStatus = fileStatusRepository.save(fileStatus);

        return IFileStatusMapper.INSTANCE.toDto(fileStatus);
    }

    @Override
    public void deleteFileStatus(Long id) {
        if (!fileStatusRepository.existsById(id)) {
            throw new ResourceNotFoundException("FileStatus not found with id: " + id);
        }
        fileStatusRepository.deleteById(id);
    }
}
