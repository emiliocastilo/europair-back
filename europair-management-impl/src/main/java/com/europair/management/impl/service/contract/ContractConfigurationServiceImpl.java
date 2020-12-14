package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractConfigurationDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.contract.IContractConfigurationMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.contracts.entity.ContractConfiguration;
import com.europair.management.rest.model.contracts.repository.ContractConfigurationRepository;
import com.europair.management.rest.model.contracts.repository.ContractRepository;
import com.europair.management.rest.model.files.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContractConfigurationServiceImpl implements IContractConfigurationService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractConfigurationRepository contractConfigurationRepository;


    @Override
    public ContractConfigurationDto findById(final Long fileId, Long contractId) {
        checkIfFileAndContractExists(fileId, contractId);
        return IContractConfigurationMapper.INSTANCE.toDto(contractConfigurationRepository.findFirstByContractId(contractId)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONFIGURATION_NOT_FOUND, String.valueOf(contractId))));
    }

    @Override
    public Long saveContractConfiguration(final Long fileId, final Long contractId, ContractConfigurationDto contractConfigurationDto) {
        checkIfFileAndContractExists(fileId, contractId);
        contractConfigurationDto.setContractId(contractId);
        if (contractConfigurationDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONFIGURATION_NEW_WITH_ID, String.valueOf(contractConfigurationDto.getId()));
        }

        ContractConfiguration contractConfiguration = IContractConfigurationMapper.INSTANCE.toEntity(contractConfigurationDto);
        contractConfiguration = contractConfigurationRepository.save(contractConfiguration);

        return contractConfiguration.getId();
    }

    @Override
    public void updateContractConfiguration(final Long fileId, final Long contractId, ContractConfigurationDto contractConfigurationDto) {
        checkIfFileAndContractExists(fileId, contractId);
        ContractConfiguration contractConfiguration = contractConfigurationRepository.findFirstByContractId(contractId)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONFIGURATION_NOT_FOUND, String.valueOf(contractId)));
        if (!contractConfiguration.getId().equals(contractConfigurationDto.getId())) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONFIGURATION_MISMATCH,
                    "[contractId:" + contractId + "], [configurationId:" + contractConfigurationDto.getId() + "]");
        }
        IContractConfigurationMapper.INSTANCE.updateFromDto(contractConfigurationDto, contractConfiguration);
        contractConfiguration = contractConfigurationRepository.save(contractConfiguration);
    }

    @Override
    public void deleteContractConfiguration(final Long fileId, final Long contractId) {
        checkIfFileAndContractExists(fileId, contractId);
        ContractConfiguration contractConfiguration = contractConfigurationRepository.findFirstByContractId(contractId)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONFIGURATION_NOT_FOUND, String.valueOf(contractId)));
        contractConfigurationRepository.delete(contractConfiguration);
    }

    private void checkIfFileAndContractExists(final Long fileId, final Long contractId) {
        if (!fileRepository.existsById(fileId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NOT_FOUND, String.valueOf(fileId));
        }
        if (!contractRepository.existsById(contractId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_NOT_FOUND, String.valueOf(contractId));
        }
    }

}
