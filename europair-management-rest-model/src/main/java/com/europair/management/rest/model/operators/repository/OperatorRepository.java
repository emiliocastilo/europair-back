package com.europair.management.rest.model.operators.repository;

import com.europair.management.rest.model.operators.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long>, JpaSpecificationExecutor<Operator>, IOperatorRepositoryCustom {

    Set<Operator> findByIdIn(@NotEmpty Set<Long> ids);

}
