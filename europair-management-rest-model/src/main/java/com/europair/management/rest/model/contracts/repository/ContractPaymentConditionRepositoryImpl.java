package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.contracts.entity.ContractPaymentCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ContractPaymentConditionRepositoryImpl extends BaseRepositoryImpl<ContractPaymentCondition> implements IContractPaymentConditionRepositoryCustom {

    @Override
    public Page<ContractPaymentCondition> findContractPaymentConditionByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(ContractPaymentCondition.class, criteria, pageable);
    }

}
