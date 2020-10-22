package com.europair.management.rest.model.routes.repository;

import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.rest.model.routes.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long>, IRouteRepositoryCustom {

    List<Route> findAllByIdIn(List<Long> idList);


    @Query("SELECT route FROM Route route " +
            "WHERE route.routeState NOT IN ( :statesToAvoid )")
    List<Route> searchNotLostRoutesAndNotWon(@Param(value = "statesToAvoid") Set<RouteStatesEnum> stateToAvoid);
}
