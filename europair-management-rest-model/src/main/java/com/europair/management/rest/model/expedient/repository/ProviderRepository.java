package com.europair.management.rest.model.expedient.repository;

import com.europair.management.rest.model.expedient.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long>, IProviderRepositoryCustom {
}
