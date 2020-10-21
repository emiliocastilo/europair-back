package com.europair.management.rest.model.contributions.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ILineContributionRouteRepositoryCustom {

    Page<LineContributionRoute> findLineContributionRouteByCriteria(CoreCriteria criteria, Pageable pageable);

}
