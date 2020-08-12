package com.europair.management.rest.model.fleet.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AircraftCategoryRepositoryImpl extends BaseRepositoryImpl<AircraftCategory> implements IAircraftCategoryRepositoryCustom {

    @Override
    public Page<AircraftCategory> findAircraftCategoriesByCriteria(CoreCriteria criteria, Pageable pageable) {
        @SuppressWarnings("unchecked")
        CriteriaQuery<AircraftCategory> crit = (CriteriaQuery<AircraftCategory>) buildCriteria(criteria, AircraftCategory.class, pageable);
        Query query = createQuery(crit);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<AircraftCategory> result = query.getResultList();

        return pageable.hasPrevious() ?
                new PageImpl<>(result) :
                new PageImpl<>(result, pageable, countAircraftCategoriesByCriteria(criteria));
    }

    @Override
    public Long countAircraftCategoriesByCriteria(CoreCriteria criteria) {
        CriteriaQuery<Long> crit = buildCountCriteria(criteria, AircraftCategory.class);
        Query query = createCountQuery(crit);

        return (Long) query.getSingleResult();
    }
}
