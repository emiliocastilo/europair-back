package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.contracts.entity.ContractLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractLineRepository extends JpaRepository<ContractLine, Long>, IContractLineRepositoryCustom {
}
