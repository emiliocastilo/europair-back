package com.europair.management.rest.fleet.repository;

import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long>, AircraftRepositoryCustom {

    // ToDo: actualizar con entidades correctas
    @Query("select a from Aircraft a where " +
            "CAST(a.operator as string) like :filter " +
            "or CAST(a.aircraftType as string) like :filter " +
            "or a.id in (select ab.aircraft.id from AircraftBase ab where CAST(ab.airport as string) like :filter)")
    Page<Aircraft> findByBasicFilter(Pageable pageable, String filter);

}
