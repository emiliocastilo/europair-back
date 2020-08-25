package com.europair.management.rest.model.operators.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.operators.entity.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IOperatorRepositoryCustom {

  Page<Operator> findOperatorByCriteria(CoreCriteria criteria, Pageable pageable);
}
