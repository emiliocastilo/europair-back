package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractCancelFeeDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.contract.IContractCancelFeeMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contracts.entity.ContractCancelFee;
import com.europair.management.rest.model.contracts.repository.ContractCancelFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContractCancelFeeServiceImpl implements IContractCancelFeeService {

    @Autowired
    private ContractCancelFeeRepository contractCancelFeeRepository;

    @Override
    public Page<ContractCancelFeeDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contractCancelFeeRepository.findContractCancelFeeByCriteria(criteria, pageable)
                .map(IContractCancelFeeMapper.INSTANCE::toDto);
    }

    @Override
    public ContractCancelFeeDto findById(Long id) {
        return IContractCancelFeeMapper.INSTANCE.toDto(contractCancelFeeRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CANCEL_FEE_NOT_FOUND,
                        String.valueOf(id))));
    }

    @Override
    public Long saveContractCancelFee(ContractCancelFeeDto contractCancelFeeDto) {
        if (contractCancelFeeDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CITY_NOT_FOUND, String.valueOf(contractCancelFeeDto.getId()));
        }

        ContractCancelFee contractCancelFee = IContractCancelFeeMapper.INSTANCE.toEntity(contractCancelFeeDto);
        contractCancelFee = contractCancelFeeRepository.save(contractCancelFee);

        return contractCancelFee.getId();
    }

    @Override
    public void updateContractCancelFee(Long id, ContractCancelFeeDto contractCancelFeeDto) {
        ContractCancelFee contractCancelFee = contractCancelFeeRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CANCEL_FEE_NOT_FOUND, String.valueOf(id)));
        IContractCancelFeeMapper.INSTANCE.updateFromDto(contractCancelFeeDto, contractCancelFee);
        contractCancelFee = contractCancelFeeRepository.save(contractCancelFee);
    }

    @Override
    public void deleteContractCancelFee(Long id) {
        if (!contractCancelFeeRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.CONTRACT_CANCEL_FEE_NOT_FOUND, String.valueOf(id));
        }
        contractCancelFeeRepository.deleteById(id);
    }

}
