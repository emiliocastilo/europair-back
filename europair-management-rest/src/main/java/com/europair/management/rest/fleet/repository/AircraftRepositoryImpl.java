package com.europair.management.rest.fleet.repository;

import com.europair.management.rest.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AircraftRepositoryImpl extends BaseRepositoryImpl<Aircraft> implements AircraftRepositoryCustom {

    @Override
    public List<Aircraft> findAircraftsByCriteria(CoreCriteria criteria, Pageable pageable) {
        CriteriaQuery<Aircraft> crit = (CriteriaQuery<Aircraft>) buildCriteria(criteria, Aircraft.class);
        Query query = createQuery(crit);
//        PageableExecutionUtils.getPage()
        @SuppressWarnings("unchecked")
        List<Aircraft> result = query.getResultList();
        return result;
    }

    @Override
    public Long countAircraftsByCriteria(CoreCriteria coreCriteria, Pageable pageable) {
        CriteriaQuery<Long> crit = buildCountCriteria(coreCriteria, Aircraft.class);
        Query query = createCountQuery(crit);
        return (Long) query.getSingleResult();
    }

}
