package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileAdditionalDataDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

public interface IFileAdditionalDataService {

    Page<FileAdditionalDataDto> findAllPaginatedByFilter(Long fileId, Pageable pageable, CoreCriteria criteria);

    FileAdditionalDataDto findById(Long fileId, Long id);

    FileAdditionalDataDto saveFileAdditionalData(Long fileId, FileAdditionalDataDto fileAdditionalDataDto);

    void updateFileAdditionalData(Long fileId, Long id, FileAdditionalDataDto fileAdditionalDataDto);

    void deleteFileAdditionalData(Long fileId, Long id);

    /**
     * @param fileId                File Identifier
     * @param fileAdditionalDataDto Data to insert/update
     * @return True if created, false if updated
     */
    Pair<Boolean, Long> createOrUpdateFileAdditionalData(Long fileId, FileAdditionalDataDto fileAdditionalDataDto);
}
