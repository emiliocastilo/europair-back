package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftTypeAverageSpeedDto;
import com.europair.management.api.service.fleet.IAircraftTypeAverageSpeedController;
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
public class AircraftTypeAverageSpeedController implements IAircraftTypeAverageSpeedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftTypeAverageSpeedController.class);

    @Autowired
    private IAircraftTypeAverageSpeedService aircraftTypeAverageSpeedService;

    @Override
    public ResponseEntity<AircraftTypeAverageSpeedDto> getAircraftTypeAverageSpeedById(@NotNull Long aircraftTypeId, @NotNull Long id) {
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Starting method [getAircraftTypeAverageSpeedById] with input: id={}, aircraftTypeId={}", id, aircraftTypeId);
        AircraftTypeAverageSpeedDto dto = aircraftTypeAverageSpeedService.findById(aircraftTypeId, id);
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Ending method [getAircraftTypeAverageSpeedById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftTypeAverageSpeedDto>> getAircraftTypeAverageSpeedByFilter(@NotNull Long aircraftTypeId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Starting method [getAircraftTypeAverageSpeedByFilter] with input: aircraftTypeId={}, pageable={}, reqParam={}",
                aircraftTypeId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftTypeAverageSpeedDto> dtoPage = aircraftTypeAverageSpeedService.findAllPaginatedByFilter(aircraftTypeId, pageable, criteria);
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Ending method [getAircraftTypeAverageSpeedByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftTypeAverageSpeedDto> saveAircraftTypeAverageSpeed(@NotNull Long aircraftTypeId, @NotNull AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto) {
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Starting method [saveAircraftTypeAverageSpeed] with input: aircraftTypeId={}, aircraftTypeAverageSpeedDto={}",
                aircraftTypeId, aircraftTypeAverageSpeedDto);
        final AircraftTypeAverageSpeedDto dtoSaved = aircraftTypeAverageSpeedService.saveAircraftTypeAverageSpeed(aircraftTypeId, aircraftTypeAverageSpeedDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftTypeId, dtoSaved.getId())
                .toUri();
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Ending method [saveAircraftTypeAverageSpeed] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftTypeAverageSpeedDto> updateAircraftTypeAverageSpeed(@NotNull Long aircraftTypeId, @NotNull Long id, @NotNull AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto) {
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Starting method [updateAircraftTypeAverageSpeed] with input: id={}, aircraftTypeId={}, aircraftTypeAverageSpeedDto={}",
                id, aircraftTypeId, aircraftTypeAverageSpeedDto);
        final AircraftTypeAverageSpeedDto dtoSaved = aircraftTypeAverageSpeedService.updateAircraftTypeAverageSpeed(aircraftTypeId, id, aircraftTypeAverageSpeedDto);
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Ending method [updateAircraftTypeAverageSpeed] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftTypeAverageSpeed(@NotNull Long aircraftTypeId, @NotNull Long id) {
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Starting method [deleteAircraftTypeAverageSpeed] with input: id={}, aircraftTypeId={}",
                id, aircraftTypeId);
        aircraftTypeAverageSpeedService.deleteAircraftTypeAverageSpeed(aircraftTypeId, id);
        LOGGER.debug("[AircraftTypeAverageSpeedController] - Ending method [deleteAircraftTypeAverageSpeed] with no return.");
        return ResponseEntity.noContent().build();
    }
}
