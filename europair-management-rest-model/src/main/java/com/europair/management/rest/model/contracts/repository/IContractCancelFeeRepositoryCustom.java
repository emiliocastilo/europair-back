package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contracts.entity.ContractCancelFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IContractCancelFeeRepositoryCustom {

    Page<ContractCancelFee> findContractCancelFeeByCriteria(CoreCriteria criteria, Pageable pageable);

}
