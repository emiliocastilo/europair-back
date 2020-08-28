package com.europair.management.rest.model.files.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.files.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProviderRepositoryImpl extends BaseRepositoryImpl<Provider> implements IProviderRepositoryCustom {

    @Override
    public Page<Provider> findProvidersByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Provider.class, criteria, pageable);
    }

}
