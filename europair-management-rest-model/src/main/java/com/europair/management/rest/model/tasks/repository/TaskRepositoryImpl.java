package com.europair.management.rest.model.tasks.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.tasks.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class TaskRepositoryImpl extends BaseRepositoryImpl<Task> implements ITaskRepositoryCustom {

    @Override
    public Page<Task> findTasksByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Task.class, criteria, pageable);
    }

}
