package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.contracts.entity.ContractCancelFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ContractCancelFeeRepositoryImpl extends BaseRepositoryImpl<ContractCancelFee> implements IContractCancelFeeRepositoryCustom {

    @Override
    public Page<ContractCancelFee> findContractCancelFeeByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(ContractCancelFee.class, criteria, pageable);
    }

}
