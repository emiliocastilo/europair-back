package com.europair.management.rest.model.contributions.repository;

import com.europair.management.rest.model.contributions.entity.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long>, IContributionRepositoryCustom {

    List<Contribution> findAllByIdIn(List<Long> idList);
}
