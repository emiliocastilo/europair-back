package com.europair.management.rest.model.files.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.files.entity.FileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FileStatusRepositoryImpl extends BaseRepositoryImpl<FileStatus> implements IFileStatusRepositoryCustom {

    @Override
    public Page<FileStatus> findFileStatusByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(FileStatus.class, criteria, pageable);
    }

}
