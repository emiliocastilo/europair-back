package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractConfigurationDto;
import com.europair.management.impl.mappers.contract.IContractConfigurationMapper;
import com.europair.management.rest.model.contracts.entity.ContractConfiguration;
import com.europair.management.rest.model.contracts.repository.ContractConfigurationRepository;
import com.europair.management.rest.model.contracts.repository.ContractRepository;
import com.europair.management.rest.model.files.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No configuration found for contract with id: " + contractId)));
    }

    @Override
    public Long saveContractConfiguration(final Long fileId, final Long contractId, ContractConfigurationDto contractConfigurationDto) {
        checkIfFileAndContractExists(fileId, contractId);
        contractConfigurationDto.setContractId(contractId);
        if (contractConfigurationDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Contract configuration expected. Identifier %s got", contractConfigurationDto.getId()));
        }

        ContractConfiguration contractConfiguration = IContractConfigurationMapper.INSTANCE.toEntity(contractConfigurationDto);
        contractConfiguration = contractConfigurationRepository.save(contractConfiguration);

        return contractConfiguration.getId();
    }

    @Override
    public void updateContractConfiguration(final Long fileId, final Long contractId, ContractConfigurationDto contractConfigurationDto) {
        checkIfFileAndContractExists(fileId, contractId);
        ContractConfiguration contractConfiguration = contractConfigurationRepository.findFirstByContractId(contractId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No configuration found for contract with id: " + contractId));
        if (!contractConfiguration.getId().equals(contractConfigurationDto.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No configuration  with id: " + contractConfigurationDto.getId()
                    + "found for contract with id: " + contractId);
        }
        IContractConfigurationMapper.INSTANCE.updateFromDto(contractConfigurationDto, contractConfiguration);
        contractConfiguration = contractConfigurationRepository.save(contractConfiguration);
    }

    @Override
    public void deleteContractConfiguration(final Long fileId, final Long contractId) {
        checkIfFileAndContractExists(fileId, contractId);
        ContractConfiguration contractConfiguration = contractConfigurationRepository.findFirstByContractId(contractId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No configuration found for contract with id: " + contractId));
        contractConfigurationRepository.delete(contractConfiguration);
    }

    private void checkIfFileAndContractExists(final Long fileId, final Long contractId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
        if (!contractRepository.existsById(contractId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + contractId);
        }
    }

}
