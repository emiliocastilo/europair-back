package com.europair.management.rest.model.expedient.repository;

import com.europair.management.rest.model.expedient.entity.ExpedientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedientStatusRepository extends JpaRepository<ExpedientStatus, Long>, IExpedientStatusRepositoryCustom {
}
