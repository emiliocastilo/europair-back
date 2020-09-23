package com.europair.management.rest.model.services.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.services.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IServiceRepositoryCustom {

    Page<Service> findServiceByCriteria(CoreCriteria criteria, Pageable pageable);

}
