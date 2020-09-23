package com.europair.management.rest.model.services.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.services.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ServiceRepositoryImpl extends BaseRepositoryImpl<Service> implements IServiceRepositoryCustom {

    @Override
    public Page<Service> findServiceByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Service.class, criteria, pageable);
    }
}
