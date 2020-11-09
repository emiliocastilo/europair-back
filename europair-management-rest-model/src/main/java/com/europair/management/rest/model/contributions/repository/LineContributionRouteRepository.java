package com.europair.management.rest.model.contributions.repository;

import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineContributionRouteRepository extends JpaRepository<LineContributionRoute, Long>, ILineContributionRouteRepositoryCustom {

    List<LineContributionRoute> findByRouteId(Long routeId);

    List<LineContributionRoute> findByRouteIdAndRemovedAtIsNull(Long id);
}
