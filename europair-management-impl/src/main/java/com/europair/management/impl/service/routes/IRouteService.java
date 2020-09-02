package com.europair.management.impl.service.routes;

import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRouteService {

    Page<RouteDto> findAllPaginatedByFilter(Long fileId, Pageable pageable, CoreCriteria criteria);

    RouteDto findById(Long fileId, Long id);

    RouteDto saveRoute(Long fileId, RouteDto routeDto);

    RouteDto updateRoute(Long fileId, Long id, RouteDto routeDto);

    void deleteRoute(Long fileId, Long id);
}
