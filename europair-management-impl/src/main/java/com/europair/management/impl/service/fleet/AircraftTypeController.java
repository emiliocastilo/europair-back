package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeDto;
import com.europair.management.api.service.fleet.IAircraftTypeController;
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
public class AircraftTypeController implements IAircraftTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftTypeController.class);

    @Autowired
    private IAircraftTypeService aircraftTypeService;

    @Override
    public ResponseEntity<AircraftTypeDto> getAircraftTypeById(@NotNull Long id) {
        LOGGER.debug("[AircraftTypeController] - Starting method [getAircraftTypeById] with input: id={}", id);
        AircraftTypeDto dto = aircraftTypeService.findById(id);
        LOGGER.debug("[AircraftTypeController] - Ending method [getAircraftTypeById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftTypeDto>> getAircraftTypeByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AircraftTypeController] - Starting method [getAircraftTypeByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftTypeDto> dtoPage = aircraftTypeService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[AircraftTypeController] - Ending method [getAircraftTypeByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftTypeDto> saveAircraftType(@NotNull AircraftTypeDto aircraftTypeDto) {
        LOGGER.debug("[AircraftTypeController] - Starting method [saveAircraftType] with input: aircraftTypeDto={}", aircraftTypeDto);
        final AircraftTypeDto dtoSaved = aircraftTypeService.saveAircraftType(aircraftTypeDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();
        LOGGER.debug("[AircraftTypeController] - Ending method [saveAircraftType] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftTypeDto> updateAircraftType(@NotNull Long id, @NotNull AircraftTypeDto aircraftTypeDto) {
        LOGGER.debug("[AircraftTypeController] - Starting method [updateAircraftType] with input: id={}, aircraftTypeDto={}", id, aircraftTypeDto);
        final AircraftTypeDto dtoSaved = aircraftTypeService.updateAircraftType(id, aircraftTypeDto);
        LOGGER.debug("[AircraftTypeController] - Ending method [updateAircraftType] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftType(@NotNull Long id) {
        LOGGER.debug("[AircraftTypeController] - Starting method [deleteAircraftType] with input: id={}", id);
        aircraftTypeService.deleteAircraftType(id);
        LOGGER.debug("[AircraftTypeController] - Ending method [deleteAircraftType] with no return.");
        return ResponseEntity.noContent().build();
    }

}
