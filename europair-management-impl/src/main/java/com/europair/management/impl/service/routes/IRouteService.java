package com.europair.management.impl.service.routes;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.dto.routes.RouteExtendedDto;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRouteService {

    Page<RouteExtendedDto> findAllPaginatedByFilter(Long fileId, Pageable pageable, CoreCriteria criteria);

    RouteDto findById(Long fileId, Long id);

    RouteDto saveRoute(Long fileId, RouteExtendedDto routeDto);

    RouteDto updateRoute(Long fileId, Long id, RouteDto routeDto);

    void deleteRoute(Long fileId, Long id);

    RouteDto updateRouteRotation(Long routeId, Long id, RouteDto routeDto);

    void updateStates(Long fileId, List<Long> routeIds, RouteStatesEnum state);

    List<String> getValidRouteStatesToChange(Long id);

    List<ContributionDTO> getContributionUsingRouteId(Long idRoute);
}
