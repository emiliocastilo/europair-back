package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContractService {

    Page<ContractDto> findAllPaginatedByFilter(Long fileId, Pageable pageable, CoreCriteria criteria);

    ContractDto findById(Long fileId, Long id);

    ContractDto saveContract(Long fileId, ContractDto contractDto);

    ContractDto updateContract(Long fileId, Long id, ContractDto contractDto);

    void deleteContract(Long fileId, Long id);
}
