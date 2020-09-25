package com.europair.management.rest.model.services.repository;

import com.europair.management.rest.model.services.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>, IServiceRepositoryCustom {
}
