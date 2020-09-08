package com.europair.management.rest.model.routes.repository;

import com.europair.management.rest.model.routes.entity.RouteFrequencyDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteFrequencyDayRepository extends JpaRepository<RouteFrequencyDay, Long> {

    @Modifying
    @Query("delete from RouteFrequencyDay rf where rf.route.id = :routeId")
    void removeByRouteId(@Param("routeId") Long routeId);

}
