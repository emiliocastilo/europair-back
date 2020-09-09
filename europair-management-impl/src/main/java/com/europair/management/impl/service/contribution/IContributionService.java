package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContributionService {

    Page<ContributionDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ContributionDTO findById(Long id);

    ContributionDTO saveContribution(ContributionDTO contributionDTO);

    ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO);

    void deleteContribution(Long id);
}
