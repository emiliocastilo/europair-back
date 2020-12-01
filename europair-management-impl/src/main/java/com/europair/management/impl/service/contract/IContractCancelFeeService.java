package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractCancelFeeDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContractCancelFeeService {

    Page<ContractCancelFeeDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ContractCancelFeeDto findById(Long id);

    ContractCancelFeeDto saveContractCancelFee(ContractCancelFeeDto contractCancelFeeDto);

    ContractCancelFeeDto updateContractCancelFee(Long id, ContractCancelFeeDto contractCancelFeeDto);

    void deleteContractCancelFee(Long id);
}
