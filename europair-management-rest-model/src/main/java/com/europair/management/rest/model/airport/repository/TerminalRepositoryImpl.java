package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.Terminal;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class TerminalRepositoryImpl extends BaseRepositoryImpl<Terminal> implements ITerminalRepositoryCustom {

    @Override
    public Page<Terminal> findTerminalByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Terminal.class, criteria, pageable);
    }
}
