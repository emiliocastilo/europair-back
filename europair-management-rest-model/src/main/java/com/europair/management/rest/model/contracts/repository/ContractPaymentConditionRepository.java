package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.contracts.entity.ContractPaymentCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractPaymentConditionRepository extends JpaRepository<ContractPaymentCondition, Long>, IContractPaymentConditionRepositoryCustom {
}
