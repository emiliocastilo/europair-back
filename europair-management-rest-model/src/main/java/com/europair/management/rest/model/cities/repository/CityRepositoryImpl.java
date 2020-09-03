package com.europair.management.rest.model.cities.repository;

import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CityRepositoryImpl extends BaseRepositoryImpl<City> implements ICityRepositoryCustom {

    @Override
    public Page<City> findCityByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(City.class, criteria, pageable);
    }
}
