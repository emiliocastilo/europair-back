package com.europair.management.rest.model.countries.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.countries.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICountryRepositoryCustom {

    Page<Country> findCountryByCriteria(CoreCriteria criteria, Pageable pageable);
}
