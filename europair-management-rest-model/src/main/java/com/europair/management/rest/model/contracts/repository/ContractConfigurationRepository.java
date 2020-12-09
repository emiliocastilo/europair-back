package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.contracts.entity.ContractConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractConfigurationRepository extends JpaRepository<ContractConfiguration, Long> {

    Optional<ContractConfiguration> findFirstByContractId(Long contractId);

}
