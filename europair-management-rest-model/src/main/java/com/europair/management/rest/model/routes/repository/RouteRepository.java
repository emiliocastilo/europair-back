package com.europair.management.rest.model.routes.repository;

import com.europair.management.rest.model.routes.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long>, IRouteRepositoryCustom {

    List<Route> findAllByIdIn(List<Long> idList);

}
