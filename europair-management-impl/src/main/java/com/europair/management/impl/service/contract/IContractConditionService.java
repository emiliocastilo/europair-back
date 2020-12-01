package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractConditionDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContractConditionService {

    Page<ContractConditionDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ContractConditionDto findById(Long id);

    ContractConditionDto saveContractCondition(ContractConditionDto contractConditionDto);

    ContractConditionDto updateContractCondition(Long id, ContractConditionDto contractConditionDto);

    void deleteContractCondition(Long id);
}
