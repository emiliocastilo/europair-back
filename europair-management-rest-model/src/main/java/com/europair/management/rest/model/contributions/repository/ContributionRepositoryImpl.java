package com.europair.management.rest.model.contributions.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.contributions.entity.Contribution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ContributionRepositoryImpl extends BaseRepositoryImpl<Contribution> implements IContributionRepositoryCustom {

    @Override
    public Page<Contribution> findContributionByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Contribution.class, criteria, pageable);
    }

}
