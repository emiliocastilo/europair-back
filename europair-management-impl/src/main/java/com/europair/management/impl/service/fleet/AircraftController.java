package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.service.fleet.IAircraftController;
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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;
import java.util.Set;

@RestController
@Slf4j
public class AircraftController implements IAircraftController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftController.class);

    @Autowired
    private IAircraftService aircraftService;

    public ResponseEntity<AircraftDto> getAircraftById(@NotNull final Long id) {
        LOGGER.debug("[AircraftController] - Starting method [getAircraftById] with input: id={}", id);
        final AircraftDto aircraftDto = aircraftService.findById(id);
        LOGGER.debug("[AircraftController] - Ending method [getAircraftById] with return: {}", aircraftDto);
        return ResponseEntity.ok(aircraftDto);
    }

    public ResponseEntity<Page<AircraftDto>> getAircraftByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AircraftController] - Starting method [getAircraftByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftDto> aircraftDtoPage = aircraftService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[AircraftController] - Ending method [getAircraftByFilter] with return: {}", pageable);
        return ResponseEntity.ok(aircraftDtoPage);
    }

    public ResponseEntity<AircraftDto> saveAircraft(@NotNull final AircraftDto aircraftDto) {
        LOGGER.debug("[AircraftController] - Starting method [saveAircraft] with input: aircraftDto={}", aircraftDto);
        final AircraftDto aircraftDtoSaved = aircraftService.saveAircraft(aircraftDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftDtoSaved.getId())
                .toUri();
        LOGGER.debug("[AircraftController] - Ending method [getAircraftById] with return: {}", aircraftDtoSaved);
        return ResponseEntity.created(location).body(aircraftDtoSaved);

    }

    public ResponseEntity<AircraftDto> updateAircraft(@NotNull final Long id, @NotNull final AircraftDto aircraftDto) {
        LOGGER.debug("[AircraftController] - Starting method [updateAircraft] with input: id={}, aircraftDto={}", id, aircraftDto);
        final AircraftDto aircraftDtoSaved = aircraftService.updateAircraft(id, aircraftDto);
        LOGGER.debug("[AircraftController] - Ending method [updateAircraft] with return: {}", aircraftDtoSaved);
        return ResponseEntity.ok().body(aircraftDtoSaved);
    }

    public ResponseEntity<?> deleteAircraft(@NotNull final Long id) {
        LOGGER.debug("[AircraftController] - Starting method [deleteAircraft] with input: id={}", id);
        aircraftService.deleteAircraft(id);
        LOGGER.debug("[AircraftController] - Ending method [deleteAircraft] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> reactivateAircraft(@NotEmpty Set<Long> aircraftIds) {
        LOGGER.debug("[AircraftController] - Starting method [reactivateAircraft] with input: aircraftIds={}", aircraftIds);
        aircraftService.reactivateAircraft(aircraftIds);
        LOGGER.debug("[AircraftController] - Ending method [reactivateAircraft] with no return.");
        return ResponseEntity.noContent().build();
    }
}
