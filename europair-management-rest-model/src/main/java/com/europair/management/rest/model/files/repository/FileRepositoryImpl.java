package com.europair.management.rest.model.files.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.files.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FileRepositoryImpl extends BaseRepositoryImpl<File> implements IFileRepositoryCustom {

  @Override
  public Page<File> findFilesByCriteria(CoreCriteria criteria, Pageable pageable) {
    return findPageByCriteria(File.class, criteria, pageable);
  }

}
