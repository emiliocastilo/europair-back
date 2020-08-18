package com.europair.management.rest.model.operatorscertifications.repository;

import com.europair.management.rest.model.operatorscertifications.entity.OperatorsAirports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOperatorsAirportsRepository extends JpaRepository<OperatorsAirports, Long> {

  Page<OperatorsAirports> findByOperatorId(Long operatorId, Pageable pageable);

}
