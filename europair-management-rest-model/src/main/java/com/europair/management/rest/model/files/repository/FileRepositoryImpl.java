package com.europair.management.rest.model.files.repository;

import com.europair.management.api.enums.FileStatesEnum;
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
  public boolean canChangeState(FileStatesEnum stateFrom, FileStatesEnum stateTo) {
    return switch (stateFrom) {
      case NEW_REQUEST -> FileStatesEnum.SALES.equals(stateTo);
      case SALES -> FileStatesEnum.OPTIONED.equals(stateTo) || FileStatesEnum.BLUE_BOOKED.equals(stateTo) || FileStatesEnum.CNX.equals(stateTo);
      case OPTIONED -> FileStatesEnum.BLUE_BOOKED.equals(stateTo) || FileStatesEnum.CNX.equals(stateTo);
      case BLUE_BOOKED -> FileStatesEnum.GREEN_BOOKED.equals(stateTo) || FileStatesEnum.CNX.equals(stateTo);
      case GREEN_BOOKED -> FileStatesEnum.PREFLIGHT.equals(stateTo) || FileStatesEnum.CNX.equals(stateTo);
      default -> false;
    };
  }
}
