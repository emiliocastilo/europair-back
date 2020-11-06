package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.contribution.LineContributionRouteDTO;
import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IContributionService {

    Page<ContributionDTO> findAllPaginatedByFilter(Long routeId, Pageable pageable, CoreCriteria criteria);

    Page<LineContributionRouteDTO> findAllPaginatedLineContributionRouteByFilter(Long contributionId, Pageable pageable, CoreCriteria criteria);

    ContributionDTO findById(Long id);

    ContributionDTO saveContribution(ContributionDTO contributionDTO);

    Long saveLineContributionRoute(LineContributionRouteDTO lineContributionRouteDTO);

    ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO);

    Boolean updateLineContributionRoute(Long routeId, Long contributionId, Long lineContributionRouteId, LineContributionRouteDTO lineContributionRouteDTO);

    void deleteContribution(Long id);

    void deleteLineContributionRoute(Long contributionId, Long lineContributionRouteId);

    void updateStates(Long fileId, Long routeId, List<Long> contributionIds, ContributionStatesEnum state);

    List<String> getValidContributionStatesToChange(Long fileId, Long routeId, Long id);

    void generateRouteContributionSaleLines(Long contributionId);
}
