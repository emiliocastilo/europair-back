package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.contribution.LineContributionRouteDTO;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IContributionService {

    Page<ContributionDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    Page<LineContributionRouteDTO> findAllPaginatedLineContributionRouteByFilter(Pageable pageable, CoreCriteria criteria);

    ContributionDTO findById(Long id);

    ContributionDTO saveContribution(ContributionDTO contributionDTO);

    Long saveLineContributionRoute(LineContributionRouteDTO lineContributionRouteDTO);

    ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO);

    Boolean updateLineContributionRoute(Long contributionId, Long lineContributionRouteId, LineContributionRouteDTO lineContributionRouteDTO);

    void deleteContribution(Long id);

    void deleteLineContributionRoute(Long contributionId,Long lineContributionRouteId);

    void generateRouteContributionSaleLines(Long contributionId);
}
