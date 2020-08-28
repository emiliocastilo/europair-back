package com.europair.management.rest.model.files.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.files.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IProviderRepositoryCustom {

    Page<Provider> findProvidersByCriteria(CoreCriteria criteria, Pageable pageable);

}
