package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.contracts.entity.ContractCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ContractConditionRepositoryImpl extends BaseRepositoryImpl<ContractCondition> implements IContractConditionRepositoryCustom {

    @Override
    public Page<ContractCondition> findContractConditionByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(ContractCondition.class, criteria, pageable);
    }

}
