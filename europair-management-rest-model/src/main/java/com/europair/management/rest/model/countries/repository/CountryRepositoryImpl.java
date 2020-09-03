package com.europair.management.rest.model.countries.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.countries.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CountryRepositoryImpl extends BaseRepositoryImpl<Country> implements ICountryRepositoryCustom {

    @Override
    public Page<Country> findCountryByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Country.class, criteria, pageable);
    }
}
