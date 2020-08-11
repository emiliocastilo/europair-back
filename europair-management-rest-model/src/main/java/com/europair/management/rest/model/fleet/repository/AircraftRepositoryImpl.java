package com.europair.management.rest.model.fleet.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AircraftRepositoryImpl extends BaseRepositoryImpl<Aircraft> implements AircraftRepositoryCustom {

    @Override
    public Page<Aircraft> findAircraftsByCriteria(CoreCriteria criteria, Pageable pageable) {
        CriteriaQuery<Aircraft> crit = (CriteriaQuery<Aircraft>) buildCriteria(criteria, Aircraft.class, pageable);
        Query query = createQuery(crit);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Aircraft> result = query.getResultList();

        Page<Aircraft> page = pageable.hasPrevious() ?
                new PageImpl(result) :
                new PageImpl(result, pageable, countAircraftsByCriteria(criteria));

        return page;
    }

    @Override
    public Long countAircraftsByCriteria(CoreCriteria coreCriteria) {
        CriteriaQuery<Long> crit = buildCountCriteria(coreCriteria, Aircraft.class);
        Query query = createCountQuery(crit);
        return (Long) query.getSingleResult();
    }

}
