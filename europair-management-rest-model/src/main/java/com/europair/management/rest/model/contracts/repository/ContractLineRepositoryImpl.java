package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.contracts.entity.ContractLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ContractLineRepositoryImpl extends BaseRepositoryImpl<ContractLine> implements IContractLineRepositoryCustom {

    @Override
    public Page<ContractLine> findContractLineByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(ContractLine.class, criteria, pageable);
    }

}
