package com.europair.management.rest.model.cities.repository;

import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICityRepositoryCustom {

    Page<City> findCityByCriteria(CoreCriteria criteria, Pageable pageable);
}
