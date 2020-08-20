package com.europair.management.rest.model.operators.repository;

import com.europair.management.rest.model.operators.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOperatorRepository extends JpaRepository<Operator, Long>, JpaSpecificationExecutor<Operator> {

  List<Operator> findByName(final String name);

}
