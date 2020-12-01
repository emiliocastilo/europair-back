package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractCancelFeeDto;
import com.europair.management.impl.mappers.contract.IContractCancelFeeMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contracts.entity.ContractCancelFee;
import com.europair.management.rest.model.contracts.repository.ContractCancelFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ContractCancelFee not found with id: " + id)));
    }

    @Override
    public ContractCancelFeeDto saveContractCancelFee(ContractCancelFeeDto contractCancelFeeDto) {
        if (contractCancelFeeDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New ContractCancelFee expected. Identifier %s got", contractCancelFeeDto.getId()));
        }

        ContractCancelFee contractCancelFee = IContractCancelFeeMapper.INSTANCE.toEntity(contractCancelFeeDto);
        contractCancelFee = contractCancelFeeRepository.save(contractCancelFee);

        return IContractCancelFeeMapper.INSTANCE.toDto(contractCancelFee);
    }

    @Override
    public ContractCancelFeeDto updateContractCancelFee(Long id, ContractCancelFeeDto contractCancelFeeDto) {
        ContractCancelFee contractCancelFee = contractCancelFeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ContractCancelFee not found with id: " + id));
        IContractCancelFeeMapper.INSTANCE.updateFromDto(contractCancelFeeDto, contractCancelFee);
        contractCancelFee = contractCancelFeeRepository.save(contractCancelFee);

        return IContractCancelFeeMapper.INSTANCE.toDto(contractCancelFee);
    }

    @Override
    public void deleteContractCancelFee(Long id) {
        if (!contractCancelFeeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ContractCancelFee not found with id: " + id);
        }
        contractCancelFeeRepository.deleteById(id);
    }

}
