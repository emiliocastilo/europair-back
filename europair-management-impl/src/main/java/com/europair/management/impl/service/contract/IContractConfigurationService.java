package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractConfigurationDto;

public interface IContractConfigurationService {

    ContractConfigurationDto findById(Long fileId, Long contractId);

    Long saveContractConfiguration(Long fileId, Long contractId, ContractConfigurationDto contractConfigurationDto);

    void updateContractConfiguration(Long fileId, Long contractId, ContractConfigurationDto contractConfigurationDto);

    void deleteContractConfiguration(Long fileId, Long contractId);
}
