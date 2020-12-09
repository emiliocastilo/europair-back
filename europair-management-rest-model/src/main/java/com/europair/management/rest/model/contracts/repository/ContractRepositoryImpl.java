package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.contracts.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ContractRepositoryImpl extends BaseRepositoryImpl<Contract> implements IContractRepositoryCustom {

    @Override
    public Page<Contract> findContractByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Contract.class, criteria, pageable);
    }

}
