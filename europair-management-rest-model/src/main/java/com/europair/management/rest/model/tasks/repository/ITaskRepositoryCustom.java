package com.europair.management.rest.model.tasks.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.tasks.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ITaskRepositoryCustom {

    Page<Task> findTasksByCriteria(CoreCriteria criteria, Pageable pageable);

    Long countTasksByCriteria(CoreCriteria criteria);
}
