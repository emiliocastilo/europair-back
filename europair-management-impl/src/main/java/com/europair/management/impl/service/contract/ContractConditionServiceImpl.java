package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractConditionCopyDto;
import com.europair.management.api.dto.contract.ContractConditionDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.contract.IContractConditionMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contracts.entity.ContractCondition;
import com.europair.management.rest.model.contracts.repository.ContractConditionRepository;
import com.europair.management.rest.model.contracts.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContractConditionServiceImpl implements IContractConditionService {

    @Autowired
    private ContractConditionRepository contractConditionRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Override
    public Page<ContractConditionDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contractConditionRepository.findContractConditionByCriteria(criteria, pageable)
                .map(IContractConditionMapper.INSTANCE::toDto);
    }

    @Override
    public ContractConditionDto findById(Long id) {
        return IContractConditionMapper.INSTANCE.toDto(contractConditionRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONDITION_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public Long saveContractCondition(ContractConditionDto contractConditionDto) {
        if (contractConditionDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONDITION_NEW_WITH_ID, String.valueOf(contractConditionDto.getId()));
        }

        ContractCondition contractCondition = IContractConditionMapper.INSTANCE.toEntity(contractConditionDto);
        contractCondition = contractConditionRepository.save(contractCondition);

        return contractCondition.getId();
    }

    @Override
    public void updateContractCondition(Long id, ContractConditionDto contractConditionDto) {
        ContractCondition contractCondition = contractConditionRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONDITION_NOT_FOUND, String.valueOf(id)));
        IContractConditionMapper.INSTANCE.updateFromDto(contractConditionDto, contractCondition);
        contractCondition = contractConditionRepository.save(contractCondition);
    }

    @Override
    public void deleteContractCondition(Long id) {
        if (!contractConditionRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONDITION_NOT_FOUND, String.valueOf(id));
        }
        contractConditionRepository.deleteById(id);
    }

    @Override
    public void copyContractConditions(ContractConditionCopyDto contractConditionCopyDto) {
        if (!contractRepository.existsById(contractConditionCopyDto.getContractId())) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_NOT_FOUND, String.valueOf(contractConditionCopyDto.getContractId()));
        }

        Set<ContractCondition> conditions = contractConditionRepository.findByIdIn(contractConditionCopyDto.getConditions());

        if (contractConditionCopyDto.getConditions().size() != conditions.size()) {
            Set<Long> conditionsFound = conditions.stream()
                    .map(ContractCondition::getId)
                    .collect(Collectors.toSet());
            String conditionsNotFound = contractConditionCopyDto.getConditions().stream()
                    .filter(id -> !conditionsFound.contains(id))
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CONDITION_NOT_FOUND_MULTIPLE, conditionsNotFound);
        }

        List<ContractCondition> copiedConditions = conditions.stream()
                .map(condition -> {
                    ContractCondition c = new ContractCondition();
                    IContractConditionMapper.INSTANCE.copyFromEntity(condition, c);
                    c.setContractId(contractConditionCopyDto.getContractId());
                    return c;
                }).collect(Collectors.toList());

        copiedConditions = contractConditionRepository.saveAll(copiedConditions);
    }
}
