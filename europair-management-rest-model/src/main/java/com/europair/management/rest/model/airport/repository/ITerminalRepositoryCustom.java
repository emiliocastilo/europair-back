package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.Terminal;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ITerminalRepositoryCustom {

    Page<Terminal> findTerminalByCriteria(CoreCriteria criteria, Pageable pageable);
}