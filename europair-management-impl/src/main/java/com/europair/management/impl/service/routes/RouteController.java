package com.europair.management.impl.service.routes;


import com.europair.management.api.dto.common.StateChangeDto;
import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.dto.routes.RouteExtendedDto;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.api.service.routes.IRouteController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class RouteController implements IRouteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private IRouteService routeService;

    @Override
    public ResponseEntity<RouteDto> getRouteById(@NotNull Long fileId, @NotNull Long id) {
        LOGGER.debug("[RouteController] - Starting method [getRouteById] with input: id={}, fileId={}", id, fileId);
        RouteDto dto = routeService.findById(fileId, id);
        LOGGER.debug("[RouteController] - Ending method [getRouteById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<RouteExtendedDto>> getRouteByFilter(@NotNull Long fileId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[RouteController] - Starting method [getRouteByFilter] with input: fileId={}, pageable={}, reqParam={}", fileId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<RouteExtendedDto> dtoPage = routeService.findAllPaginatedByFilter(fileId, pageable, criteria);
        LOGGER.debug("[RouteController] - Ending method [getRouteByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<RouteDto> saveRoute(@NotNull Long fileId, @NotNull RouteExtendedDto routeDto) {
        LOGGER.debug("[RouteController] - Starting method [saveRoute] with input: fileId={}, routeDto={}", fileId, routeDto);
        final RouteDto dtoSaved = routeService.saveRoute(fileId, routeDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileId, dtoSaved.getId())
                .toUri();
        LOGGER.debug("[RouteController] - Ending method [saveRoute] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<RouteDto> updateRoute(@NotNull Long fileId, @NotNull Long id, @NotNull RouteDto routeDto) {
        LOGGER.debug("[RouteController] - Starting method [updateRoute] with input: id={}, fileId={}, routeDto={}", id, fileId, routeDto);
        final RouteDto dtoSaved = routeService.updateRoute(fileId, id, routeDto);
        LOGGER.debug("[RouteController] - Ending method [updateRoute] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteRoute(@NotNull Long fileId, @NotNull Long id) {
        LOGGER.debug("[RouteController] - Starting method [deleteRoute] with input: id={}, fileId={}", id, fileId);
        routeService.deleteRoute(fileId, id);
        LOGGER.debug("[RouteController] - Ending method [deleteRoute] with no return.");
        return ResponseEntity.noContent().build();
    }

    // Rotations

    @Override
    public ResponseEntity<RouteDto> updateRotation(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long id,
                                                   @NotNull RouteDto routeDto) {
        LOGGER.debug("[RouteController] - Starting method [updateRotation] with input: id={}, fileId={}, routeId={}, routeDto={}",
                id, fileId, routeId, routeDto);
        final RouteDto dtoSaved = routeService.updateRouteRotation(routeId, id, routeDto);
        LOGGER.debug("[RouteController] - Ending method [updateRotation] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> changeState(@NotNull Long fileId, @NotNull StateChangeDto<RouteStatesEnum> stateChangeDto) {
        LOGGER.debug("[RouteController] - Starting method [changeState] with input: fileId={}, stateChangeDto={}", fileId, stateChangeDto);
        routeService.updateStates(fileId, stateChangeDto.getIdList(), stateChangeDto.getState());
        LOGGER.debug("[RouteController] - Ending method [changeState] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<String>> getValidRouteStatesToChange(@NotNull Long id) {
        LOGGER.debug("[RouteController] - Starting method [getValidRouteStatesToChange] with input: id={}", id);
        List<String> res = routeService.getValidRouteStatesToChange(id);
        LOGGER.debug("[RouteController] - Ending method [getValidRouteStatesToChange] with return: {}", res);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<List<ContributionDTO>> getRouteWithContributions(Long routeId) {
        LOGGER.debug("[RouteController] - Starting method [getRouteWithContributions] with input: routeId={}", routeId);
        List<ContributionDTO> contributionDTOS = routeService.getContributionUsingRouteId(routeId);
        LOGGER.debug("[RouteController] - Ending method [getRouteWithContributions] with return: {}", contributionDTOS);
        return ResponseEntity.ok(contributionDTOS);
    }
}
