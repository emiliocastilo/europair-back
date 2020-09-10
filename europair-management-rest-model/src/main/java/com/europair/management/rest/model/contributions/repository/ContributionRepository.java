package com.europair.management.rest.model.contributions.repository;

import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.repository.IAircraftRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long>, IContributionRepositoryCustom {
}
