package com.europair.management.rest.model.servicetypes.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.servicetypes.entity.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IServiceTypeRepositoryCustom {

  Page<ServiceType> findServiceTypeByCriteria(CoreCriteria criteria, Pageable pageable);

}
