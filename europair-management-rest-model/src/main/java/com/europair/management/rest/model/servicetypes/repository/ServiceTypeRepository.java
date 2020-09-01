package com.europair.management.rest.model.servicetypes.repository;

import com.europair.management.rest.model.servicetypes.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long>, IServiceTypeRepositoryCustom {
}
