package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.impl.mappers.contract.IContractMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.contracts.entity.Contract;
import com.europair.management.rest.model.contracts.repository.ContractRepository;
import com.europair.management.rest.model.files.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ContractServiceImpl implements IContractService {

    private final String FILE_ID_FILTER = "file.id";

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Page<ContractDto> findAllPaginatedByFilter(final Long fileId, Pageable pageable, CoreCriteria criteria) {
        checkIfFileExists(fileId);
        Utils.addCriteriaIfNotExists(criteria, FILE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(fileId));

        return contractRepository.findContractByCriteria(criteria, pageable)
                .map(IContractMapper.INSTANCE::toDto);
    }

    @Override
    public ContractDto findById(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        return IContractMapper.INSTANCE.toDto(contractRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + id)));
    }

    @Override
    public ContractDto saveContract(final Long fileId, ContractDto contractDto) {
        checkIfFileExists(fileId);
        contractDto.setFileId(fileId);
        if (contractDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Contract expected. Identifier %s got", contractDto.getId()));
        }

        Contract contract = IContractMapper.INSTANCE.toEntity(contractDto);
        contract = contractRepository.save(contract);

        return IContractMapper.INSTANCE.toDto(contract);
    }

    @Override
    public ContractDto updateContract(final Long fileId, Long id, ContractDto contractDto) {
        checkIfFileExists(fileId);
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + id));
        IContractMapper.INSTANCE.updateFromDto(contractDto, contract);
        contract = contractRepository.save(contract);

        return IContractMapper.INSTANCE.toDto(contract);
    }

    @Override
    public void deleteContract(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        if (!contractRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + id);
        }
        contractRepository.deleteById(id);
    }

    private void checkIfFileExists(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
    }

}
