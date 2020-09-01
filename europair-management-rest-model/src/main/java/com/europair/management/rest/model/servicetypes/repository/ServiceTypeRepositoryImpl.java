package com.europair.management.rest.model.servicetypes.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.servicetypes.entity.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ServiceTypeRepositoryImpl extends BaseRepositoryImpl<ServiceType> implements IServiceTypeRepositoryCustom {

  @Override
  public Page<ServiceType> findServiceTypeByCriteria(CoreCriteria criteria, Pageable pageable) {
    return findPageByCriteria(ServiceType.class, criteria, pageable);
  }
}
