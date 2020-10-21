package com.europair.management.rest.model.contributions.repository;

import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineContributionRouteRepository extends JpaRepository<LineContributionRoute, Long>, ILineContributionRouteRepositoryCustom {

}
