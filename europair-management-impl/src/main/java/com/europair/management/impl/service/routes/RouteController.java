package com.europair.management.impl.service.routes;


import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.service.routes.IRouteController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class RouteController implements IRouteController {

    @Autowired
    private IRouteService routeService;

    @Override
    public ResponseEntity<RouteDto> getRouteById(@NotNull Long fileId, @NotNull Long id) {
        RouteDto dto = routeService.findById(fileId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<RouteDto>> getRouteByFilter(@NotNull Long fileId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<RouteDto> dtoPage = routeService.findAllPaginatedByFilter(fileId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<RouteDto> saveRoute(@NotNull Long fileId, @NotNull RouteDto routeDto) {
        final RouteDto dtoSaved = routeService.saveRoute(fileId, routeDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<RouteDto> updateRoute(@NotNull Long fileId, @NotNull Long id, @NotNull RouteDto routeDto) {
        final RouteDto dtoSaved = routeService.updateRoute(fileId, id, routeDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteRoute(@NotNull Long fileId, @NotNull Long id) {
        routeService.deleteRoute(fileId, id);
        return ResponseEntity.noContent().build();
    }
}
