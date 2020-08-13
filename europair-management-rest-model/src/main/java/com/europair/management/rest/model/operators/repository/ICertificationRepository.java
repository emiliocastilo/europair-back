package com.europair.management.rest.model.operators.repository;

import com.europair.management.rest.model.operators.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICertificationRepository extends JpaRepository<Certification, Long> {
}
