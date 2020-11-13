package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftObservationDto;
import com.europair.management.api.service.fleet.IAircraftObservationController;
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
public class AircraftObservationController implements IAircraftObservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftObservationController.class);

    @Autowired
    private IAircraftObservationService aircraftObservationService;

    @Override
    public ResponseEntity<AircraftObservationDto> getAircraftObservationById(@NotNull Long aircraftId, @NotNull Long id) {
        LOGGER.debug("[AircraftObservationController] - Starting method [getAircraftObservationById] with input: id={}, aircraftId={}", id, aircraftId);
        AircraftObservationDto dto = aircraftObservationService.findById(aircraftId, id);
        LOGGER.debug("[AircraftObservationController] - Ending method [getAircraftObservationById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftObservationDto>> getAircraftObservationByFilter(@NotNull Long aircraftId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AircraftObservationController] - Starting method [getAircraftObservationByFilter] with input: aircraftId={}, pageable={}, reqParam={}",
                aircraftId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftObservationDto> dtoPage = aircraftObservationService.findAllPaginatedByFilter(aircraftId, pageable, criteria);
        LOGGER.debug("[AircraftObservationController] - Ending method [getAircraftObservationByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftObservationDto> saveAircraftObservation(@NotNull Long aircraftId, @NotNull AircraftObservationDto aircraftObservationDto) {
        LOGGER.debug("[AircraftObservationController] - Starting method [saveAircraftObservation] with input: aircraftId={}, aircraftObservationDto={}",
                aircraftId, aircraftObservationDto);
        final AircraftObservationDto dtoSaved = aircraftObservationService.saveAircraftObservation(aircraftId, aircraftObservationDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftId, dtoSaved.getId())
                .toUri();
        LOGGER.debug("[AircraftObservationController] - Ending method [saveAircraftObservation] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftObservationDto> updateAircraftObservation(@NotNull Long aircraftId, @NotNull Long id, @NotNull AircraftObservationDto aircraftObservationDto) {
        LOGGER.debug("[AircraftObservationController] - Starting method [updateAircraftObservation] with input: id={}, aircraftId={}, aircraftObservationDto={}",
                id, aircraftObservationDto, aircraftObservationDto);
        final AircraftObservationDto dtoSaved = aircraftObservationService.updateAircraftObservation(aircraftId, id, aircraftObservationDto);
        LOGGER.debug("[AircraftObservationController] - Ending method [updateAircraftObservation] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftObservation(@NotNull Long aircraftId, @NotNull Long id) {
        LOGGER.debug("[AircraftObservationController] - Starting method [deleteAircraftObservation] with input: id={}, aircraftId={}", id, aircraftId);
        aircraftObservationService.deleteAircraftObservation(aircraftId, id);
        LOGGER.debug("[AircraftObservationController] - Ending method [deleteAircraftObservation] with no return.");
        return ResponseEntity.noContent().build();
    }
}
