package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileStatusDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFileStatusService {

    Page<FileStatusDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    FileStatusDto findById(Long id);

    FileStatusDto saveFileStatus(FileStatusDto fileStatusDto);

    FileStatusDto updateFileStatus(Long id, FileStatusDto fileStatusDto);

    void deleteFileStatus(Long id);
}
