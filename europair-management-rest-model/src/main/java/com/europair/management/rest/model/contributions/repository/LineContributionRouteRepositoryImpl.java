package com.europair.management.rest.model.contributions.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class LineContributionRouteRepositoryImpl extends BaseRepositoryImpl<LineContributionRoute> implements ILineContributionRouteRepositoryCustom {


    @Override
    public Page<LineContributionRoute> findLineContributionRouteByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(LineContributionRoute.class, criteria, pageable);
    }

}
