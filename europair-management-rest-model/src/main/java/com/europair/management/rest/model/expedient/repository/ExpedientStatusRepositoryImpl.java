package com.europair.management.rest.model.expedient.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.expedient.entity.ExpedientStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ExpedientStatusRepositoryImpl extends BaseRepositoryImpl<ExpedientStatus> implements IExpedientStatusRepositoryCustom {

    @Override
    public Page<ExpedientStatus> findExpedientStatusByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(ExpedientStatus.class, criteria, pageable);
    }

}
