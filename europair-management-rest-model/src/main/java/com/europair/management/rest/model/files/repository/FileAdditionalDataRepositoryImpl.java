package com.europair.management.rest.model.files.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.files.entity.FileAdditionalData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FileAdditionalDataRepositoryImpl extends BaseRepositoryImpl<FileAdditionalData> implements IFileAdditionalDataRepositoryCustom {

    @Override
    public Page<FileAdditionalData> findFileAdditionalDataByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(FileAdditionalData.class, criteria, pageable);
    }

}
