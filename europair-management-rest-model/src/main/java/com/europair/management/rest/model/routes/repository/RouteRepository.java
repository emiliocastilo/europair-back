package com.europair.management.rest.model.routes.repository;

import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.rest.model.routes.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long>, IRouteRepositoryCustom {

    List<Route> findAllByIdInAndRemovedAtIsNull(List<Long> idList);

    List<Route> findByParentRouteIdAndRemovedAtIsNull(Long routeId);

    @Query("SELECT route FROM Route route " +
            "WHERE route.routeState NOT IN ( :statesToAvoid )" +
            "AND route.removedAt IS Null")
    List<Route> searchNotLostRoutesAndNotWon(@Param(value = "statesToAvoid") Set<RouteStatesEnum> stateToAvoid);

    @Query("select case when count(r)> 0 then true else false end from Route r where r.id = :routeId and r.removedAt is null")
    boolean existsByIdAndRemovedAtIsNull(Long routeId);

    Optional<Route> findByIdAndRemovedAtIsNull(Long routeId);
}
