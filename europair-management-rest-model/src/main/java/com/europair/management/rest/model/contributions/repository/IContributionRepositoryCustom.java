package com.europair.management.rest.model.contributions.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IContributionRepositoryCustom {

    Page<Contribution> findContributionByCriteria(CoreCriteria criteria, Pageable pageable);

    Page<LineContributionRoute> findLineContributionRouteByCriteria(CoreCriteria criteria, Pageable pageable);

}
