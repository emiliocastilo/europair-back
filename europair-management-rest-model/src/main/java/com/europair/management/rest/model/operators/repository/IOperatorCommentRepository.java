package com.europair.management.rest.model.operators.repository;

import com.europair.management.rest.model.operators.entity.OperatorComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOperatorCommentRepository extends JpaRepository<OperatorComment, Long> {
}
