package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.contracts.entity.ContractCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContractConditionRepository extends JpaRepository<ContractCondition, Long>, IContractConditionRepositoryCustom {

    Set<ContractCondition> findByIdIn(Set<Long> idList);
}
