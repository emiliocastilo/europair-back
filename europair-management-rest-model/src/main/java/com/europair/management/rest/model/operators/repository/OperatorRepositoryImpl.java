package com.europair.management.rest.model.operators.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.operators.entity.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OperatorRepositoryImpl extends BaseRepositoryImpl<Operator> implements IOperatorRepositoryCustom {

  @Override
  public Page<Operator> findOperatorByCriteria(CoreCriteria criteria, Pageable pageable) {
    return findPageActiveByCriteria(Operator.class, criteria, pageable);
  }
}
