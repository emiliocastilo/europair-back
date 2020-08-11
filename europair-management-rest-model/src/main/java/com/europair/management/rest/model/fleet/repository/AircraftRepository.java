package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long>, AircraftRepositoryCustom {

    // ToDo: actualizar con entidades correctas
    @Query("select a from Aircraft a join a.bases base where " +
            "CAST(a.operator as string) like :filter " +
            "or CAST(a.aircraftType as string) like :filter " +
            "or CAST(base.airport as string) like :filter")
    Page<Aircraft> findByBasicFilter(Pageable pageable, String filter);

}
