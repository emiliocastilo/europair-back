package com.europair.management.impl.service.airport;


import com.europair.management.api.dto.airport.RunwayDto;
import com.europair.management.api.service.airport.IRunwayController;
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
import java.util.Map;

@RestController
@Slf4j
public class RunwayController implements IRunwayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunwayController.class);

    @Autowired
    private IRunwayService runwayService;

    @Override
    public ResponseEntity<RunwayDto> getRunwayById(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[RunwayController] - Starting method [getRunwayById] with input: airportId={}, id={}", airportId, id);
        RunwayDto dto = runwayService.findById(airportId, id);
        LOGGER.debug("[RunwayController] - Ending method [getRunwayById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<RunwayDto>> getRunwayByFilter(@NotNull Long airportId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[RunwayController] - Starting method [getRunwayByFilter] with input: airportId={}, pageable={}, reqParam={}", airportId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<RunwayDto> dtoPage = runwayService.findAllPaginatedByFilter(airportId, pageable, criteria);
        LOGGER.debug("[RunwayController] - Ending method [getRunwayByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<RunwayDto> saveRunway(@NotNull Long airportId, @NotNull RunwayDto runwayDto) {
        LOGGER.debug("[RunwayController] - Starting method [saveRunway] with input: airportId={}, runwayDto={}", airportId, runwayDto);
        final RunwayDto dtoSaved = runwayService.saveRunway(airportId, runwayDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        LOGGER.debug("[RunwayController] - Ending method [saveRunway] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<RunwayDto> updateRunway(@NotNull Long airportId, @NotNull Long id, @NotNull RunwayDto runwayDto) {
        LOGGER.debug("[RunwayController] - Starting method [updateRunway] with input: airportId={}, runwayDto={}", airportId, runwayDto);
        final RunwayDto dtoSaved = runwayService.updateRunway(airportId, id, runwayDto);
        LOGGER.debug("[RunwayController] - Ending method [updateRunway] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteRunway(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[RunwayController] - Starting method [deleteRunway] with input: airportId={}, id={}", airportId, id);
        runwayService.deleteRunway(airportId, id);
        LOGGER.debug("[RunwayController] - Ending method [deleteRunway] with no return.");
        return ResponseEntity.noContent().build();
    }
}
