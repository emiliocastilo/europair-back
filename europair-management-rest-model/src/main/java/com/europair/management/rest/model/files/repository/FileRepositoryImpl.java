package com.europair.management.rest.model.files.repository;

import com.europair.management.api.enums.FileStates;
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

  @Override
  public boolean canChangeState(FileStates stateFrom, FileStates stateTo) {
    return switch (stateFrom) {
      case NEW_REQUEST -> FileStates.SALES.equals(stateTo);
      case SALES -> FileStates.OPTIONED.equals(stateTo) || FileStates.BOOKED.equals(stateTo) || FileStates.CNX.equals(stateTo);
      case OPTIONED -> FileStates.BOOKED.equals(stateTo) || FileStates.CNX.equals(stateTo);
      case BOOKED -> FileStates.BOOKED_SIGNED.equals(stateTo) || FileStates.CNX.equals(stateTo);
      case BOOKED_SIGNED -> FileStates.PREFLIGHT.equals(stateTo) || FileStates.CNX.equals(stateTo);
      default -> false;
    };
  }
}
