package com.europair.management.rest.model.routes.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.routes.entity.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRouteRepositoryCustom {

    Page<Route> findRouteByCriteria(CoreCriteria criteria, Pageable pageable);

}
