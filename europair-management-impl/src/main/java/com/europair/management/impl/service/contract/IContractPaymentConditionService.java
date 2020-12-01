package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractPaymentConditionDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContractPaymentConditionService {

    Page<ContractPaymentConditionDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ContractPaymentConditionDto findById(Long id);

    ContractPaymentConditionDto saveContractPaymentCondition(ContractPaymentConditionDto contractPaymentConditionDto);

    ContractPaymentConditionDto updateContractPaymentCondition(Long id, ContractPaymentConditionDto contractPaymentConditionDto);

    void deleteContractPaymentCondition(Long id);
}
