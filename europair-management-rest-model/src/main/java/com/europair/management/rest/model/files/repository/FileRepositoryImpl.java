package com.europair.management.rest.model.files.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.files.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class FileRepositoryImpl extends BaseRepositoryImpl<File> implements IFileRepositoryCustom {

  @Override
  public Page<File> findFilesByCriteria(CoreCriteria criteria, Pageable pageable) {
    return findPageByCriteria(File.class, criteria, pageable);
  }

  @Override
  public List<File> findFilesByCriteria(CoreCriteria criteria) {
    @SuppressWarnings("unchecked")
    CriteriaQuery<File> crit = (CriteriaQuery<File>) buildCriteria(criteria, File.class);
    Query query = createQuery(crit);

    @SuppressWarnings("unchecked")
    List<File> result = query.getResultList();
    return result;
  }
}
