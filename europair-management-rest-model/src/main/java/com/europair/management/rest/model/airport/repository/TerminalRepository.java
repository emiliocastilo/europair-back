package com.europair.management.rest.model.airport.repository;

import com.europair.management.rest.model.airport.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long>, ITerminalRepositoryCustom {
}
