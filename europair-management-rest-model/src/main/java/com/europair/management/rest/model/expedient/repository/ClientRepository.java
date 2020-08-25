package com.europair.management.rest.model.expedient.repository;

import com.europair.management.rest.model.expedient.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, IClientRepositoryCustom {
}
