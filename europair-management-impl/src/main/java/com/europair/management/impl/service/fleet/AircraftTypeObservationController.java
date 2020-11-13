package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftTypeObservationDto;
import com.europair.management.api.service.fleet.IAircraftTypeObservationController;
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
public class AircraftTypeObservationController implements IAircraftTypeObservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftTypeObservationController.class);

    @Autowired
    private IAircraftTypeObservationService aircraftTypeObservationService;

    @Override
    public ResponseEntity<AircraftTypeObservationDto> getAircraftTypeObservationById(@NotNull Long aircraftTypeId, @NotNull Long id) {
        LOGGER.debug("[AircraftTypeObservationController] - Starting method [getAircraftTypeObservationById] with input: id={}, aircraftTypeId={}", id, aircraftTypeId);
        AircraftTypeObservationDto dto = aircraftTypeObservationService.findById(aircraftTypeId, id);
        LOGGER.debug("[AircraftTypeObservationController] - Ending method [getAircraftTypeObservationById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftTypeObservationDto>> getAircraftTypeObservationByFilter(@NotNull Long aircraftTypeId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AircraftTypeObservationController] - Starting method [getAircraftTypeObservationByFilter] with input: aircraftTypeId={}, pageable={}, reqParam={}",
                aircraftTypeId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftTypeObservationDto> dtoPage = aircraftTypeObservationService.findAllPaginatedByFilter(aircraftTypeId, pageable, criteria);
        LOGGER.debug("[AircraftTypeObservationController] - Ending method [getAircraftTypeObservationByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftTypeObservationDto> saveAircraftTypeObservation(@NotNull Long aircraftTypeId, @NotNull AircraftTypeObservationDto aircraftTypeObservationDto) {
        LOGGER.debug("[AircraftTypeObservationController] - Starting method [saveAircraftTypeObservation] with input: aircraftTypeId={}, aircraftTypeObservationDto={}",
                aircraftTypeId, aircraftTypeObservationDto);
        final AircraftTypeObservationDto dtoSaved = aircraftTypeObservationService.saveAircraftTypeObservation(aircraftTypeId, aircraftTypeObservationDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftTypeId, dtoSaved.getId())
                .toUri();
        LOGGER.debug("[AircraftTypeObservationController] - Ending method [saveAircraftTypeObservation] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftTypeObservationDto> updateAircraftTypeObservation(@NotNull Long aircraftTypeId, @NotNull Long id, @NotNull AircraftTypeObservationDto aircraftTypeObservationDto) {
        LOGGER.debug("[AircraftTypeObservationController] - Starting method [updateAircraftTypeObservation] with input: id={}, aircraftTypeId={}, aircraftTypeObservationDto={}",
                id, aircraftTypeId, aircraftTypeObservationDto);
        final AircraftTypeObservationDto dtoSaved = aircraftTypeObservationService.updateAircraftTypeObservation(aircraftTypeId, id, aircraftTypeObservationDto);
        LOGGER.debug("[AircraftTypeObservationController] - Ending method [updateAircraftTypeObservation] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftTypeObservation(@NotNull Long aircraftTypeId, @NotNull Long id) {
        LOGGER.debug("[AircraftTypeObservationController] - Starting method [deleteAircraftTypeObservation] with input: id={}, aircraftTypeId={}",
                id, aircraftTypeId);
        aircraftTypeObservationService.deleteAircraftTypeObservation(aircraftTypeId, id);
        LOGGER.debug("[AircraftTypeObservationController] - Ending method [deleteAircraftTypeObservation] with no return.");
        return ResponseEntity.noContent().build();
    }
}
