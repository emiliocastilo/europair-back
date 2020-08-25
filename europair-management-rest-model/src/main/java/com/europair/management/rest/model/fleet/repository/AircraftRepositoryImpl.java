package com.europair.management.rest.model.fleet.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AircraftRepositoryImpl extends BaseRepositoryImpl<Aircraft> implements IAircraftRepositoryCustom {

    @Override
    public Page<Aircraft> findAircraftsByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageActiveByCriteria(Aircraft.class, criteria, pageable);
    }

    @Override
    public List<Aircraft> searchAircraft(CoreCriteria criteria) {
        @SuppressWarnings("unchecked")
        CriteriaQuery<Aircraft> crit = (CriteriaQuery<Aircraft>) buildCriteria(criteria, Aircraft.class, null);
        Query query = createQuery(crit);

        @SuppressWarnings("unchecked")
        List<Aircraft> result = query.getResultList();

        return result;
    }
}
