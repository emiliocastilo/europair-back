package com.europair.management.rest.model.contracts.repository;

import com.europair.management.rest.model.contracts.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, IContractRepositoryCustom {

    List<Contract> findByCodeStartsWith(String codeStart);

}
