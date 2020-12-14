package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractPaymentConditionDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.contract.IContractPaymentConditionMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contracts.entity.ContractPaymentCondition;
import com.europair.management.rest.model.contracts.repository.ContractPaymentConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContractPaymentConditionServiceImpl implements IContractPaymentConditionService {

    @Autowired
    private ContractPaymentConditionRepository contractPaymentConditionRepository;

    @Override
    public Page<ContractPaymentConditionDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contractPaymentConditionRepository.findContractPaymentConditionByCriteria(criteria, pageable)
                .map(IContractPaymentConditionMapper.INSTANCE::toDto);
    }

    @Override
    public ContractPaymentConditionDto findById(Long id) {
        return IContractPaymentConditionMapper.INSTANCE.toDto(contractPaymentConditionRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_PAYMENT_CONDITION_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public Long saveContractPaymentCondition(ContractPaymentConditionDto contractPaymentConditionDto) {
        if (contractPaymentConditionDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_PAYMENT_CONDITION_NEW_WITH_ID, String.valueOf(contractPaymentConditionDto.getId()));
        }

        ContractPaymentCondition contractPaymentCondition = IContractPaymentConditionMapper.INSTANCE.toEntity(contractPaymentConditionDto);
        contractPaymentCondition = contractPaymentConditionRepository.save(contractPaymentCondition);

        return contractPaymentCondition.getId();
    }

    @Override
    public void updateContractPaymentCondition(Long id, ContractPaymentConditionDto contractPaymentConditionDto) {
        ContractPaymentCondition contractPaymentCondition = contractPaymentConditionRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_PAYMENT_CONDITION_NOT_FOUND, String.valueOf(id)));
        IContractPaymentConditionMapper.INSTANCE.updateFromDto(contractPaymentConditionDto, contractPaymentCondition);
        contractPaymentCondition = contractPaymentConditionRepository.save(contractPaymentCondition);
    }

    @Override
    public void deleteContractPaymentCondition(Long id) {
        if (!contractPaymentConditionRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_PAYMENT_CONDITION_NOT_FOUND, String.valueOf(id));
        }
        contractPaymentConditionRepository.deleteById(id);
    }

}
