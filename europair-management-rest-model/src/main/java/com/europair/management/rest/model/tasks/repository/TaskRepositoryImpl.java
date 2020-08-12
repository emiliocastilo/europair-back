package com.europair.management.rest.model.tasks.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.tasks.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class TaskRepositoryImpl extends BaseRepositoryImpl<Task> implements ITaskRepositoryCustom {

    @Override
    public Page<Task> findTasksByCriteria(CoreCriteria criteria, Pageable pageable) {
        @SuppressWarnings("unchecked")
        CriteriaQuery<Task> crit = (CriteriaQuery<Task>) buildCriteria(criteria, Task.class, pageable);
        Query query = createQuery(crit);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Task> result = query.getResultList();

        return pageable.hasPrevious() ?
                new PageImpl<>(result) :
                new PageImpl<>(result, pageable, countTasksByCriteria(criteria));
    }

    @Override
    public Long countTasksByCriteria(CoreCriteria criteria) {
        CriteriaQuery<Long> crit = buildCountCriteria(criteria, Task.class);
        Query query = createCountQuery(crit);

        return (Long) query.getSingleResult();
    }
}
