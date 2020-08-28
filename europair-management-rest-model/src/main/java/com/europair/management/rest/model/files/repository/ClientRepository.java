package com.europair.management.rest.model.files.repository;

import com.europair.management.rest.model.files.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, IClientRepositoryCustom {
}
