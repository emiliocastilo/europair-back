package com.europair.management.rest.model.contributions.repository;

import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.contributions.entity.Contribution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ContributionRepositoryImpl extends BaseRepositoryImpl<Contribution> implements IContributionRepositoryCustom {

    @Override
    public Page<Contribution> findContributionByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Contribution.class, criteria, pageable);
    }

    @Override
    public boolean canChangeState(final ContributionStatesEnum stateFrom, final ContributionStatesEnum stateTo) {
        return switch (stateFrom) {
            case PENDING -> ContributionStatesEnum.SENDED.equals(stateTo);
            case SENDED -> ContributionStatesEnum.QUOTED.equals(stateTo);
            case QUOTED -> ContributionStatesEnum.CONFIRMED.equals(stateTo);
            default -> false;
        };
    }

}
