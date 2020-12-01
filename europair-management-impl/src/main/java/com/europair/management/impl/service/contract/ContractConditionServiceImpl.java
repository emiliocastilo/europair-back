package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractConditionDto;
import com.europair.management.impl.mappers.contract.IContractConditionMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contracts.entity.ContractCondition;
import com.europair.management.rest.model.contracts.repository.ContractConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ContractConditionServiceImpl implements IContractConditionService {

    @Autowired
    private ContractConditionRepository contractConditionRepository;

    @Override
    public Page<ContractConditionDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contractConditionRepository.findContractConditionByCriteria(criteria, pageable)
                .map(IContractConditionMapper.INSTANCE::toDto);
    }

    @Override
    public ContractConditionDto findById(Long id) {
        return IContractConditionMapper.INSTANCE.toDto(contractConditionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ContractCondition not found with id: " + id)));
    }

    @Override
    public ContractConditionDto saveContractCondition(ContractConditionDto contractConditionDto) {
        if (contractConditionDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New ContractCondition expected. Identifier %s got", contractConditionDto.getId()));
        }

        ContractCondition contractCondition = IContractConditionMapper.INSTANCE.toEntity(contractConditionDto);
        contractCondition = contractConditionRepository.save(contractCondition);

        return IContractConditionMapper.INSTANCE.toDto(contractCondition);
    }

    @Override
    public ContractConditionDto updateContractCondition(Long id, ContractConditionDto contractConditionDto) {
        ContractCondition contractCondition = contractConditionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ContractCondition not found with id: " + id));
        IContractConditionMapper.INSTANCE.updateFromDto(contractConditionDto, contractCondition);
        contractCondition = contractConditionRepository.save(contractCondition);

        return IContractConditionMapper.INSTANCE.toDto(contractCondition);
    }

    @Override
    public void deleteContractCondition(Long id) {
        if (!contractConditionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ContractCondition not found with id: " + id);
        }
        contractConditionRepository.deleteById(id);
    }

}
