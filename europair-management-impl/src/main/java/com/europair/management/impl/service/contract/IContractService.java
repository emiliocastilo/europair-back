package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractCompleteDataDto;
import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.api.dto.contract.ContractLineDto;
import com.europair.management.api.enums.ContractStatesEnum;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface IContractService {

    Page<ContractDto> findAllPaginatedByFilter(Long fileId, Pageable pageable, CoreCriteria criteria);

    ContractDto findById(Long fileId, Long id);

    Long saveContract(Long fileId, ContractDto contractDto);

    void updateContract(Long fileId, Long id, ContractDto contractDto);

    void deleteContract(Long fileId, Long id);

    void generateContracts(@NotNull Long fileId, @NotEmpty List<Long> routeIds);

    void updateStates(@NotNull Long fileId, @NotEmpty List<Long> contractIds, ContractStatesEnum state);

    Long copyContract(@NotNull Long fileId, @NotNull Long contractId);

    ContractCompleteDataDto getContractCompleteData(@NotNull Long fileId, @NotNull Long contractId);

    void updateContractLine(@NotNull Long fileId, @NotNull Long contractId, @NotNull Long contractLineId, @NotNull ContractLineDto contractLineDto);

    void deleteContractLine(@NotNull Long fileId, @NotNull Long contractId, @NotNull Long contractLineId);
}
