package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.contracts.entity.ContractCancelFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractCancelFeeRepository extends JpaRepository<ContractCancelFee, Long>, IContractCancelFeeRepositoryCustom {
}
