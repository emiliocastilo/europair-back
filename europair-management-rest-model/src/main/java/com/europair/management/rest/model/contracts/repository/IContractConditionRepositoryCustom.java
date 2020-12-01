package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contracts.entity.ContractCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IContractConditionRepositoryCustom {

    Page<ContractCondition> findContractConditionByCriteria(CoreCriteria criteria, Pageable pageable);

}
