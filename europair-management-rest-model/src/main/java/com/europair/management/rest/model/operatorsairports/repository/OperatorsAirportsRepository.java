package com.europair.management.rest.model.operatorsairports.repository;

import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorsAirportsRepository extends JpaRepository<OperatorsAirports, Long>, IOperatorsAirportsRepositoryCustom {

  Page<OperatorsAirports> findByOperatorId(Long operatorId, Pageable pageable);

}
