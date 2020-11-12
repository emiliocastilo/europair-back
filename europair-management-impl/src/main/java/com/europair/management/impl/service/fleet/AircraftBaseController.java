package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftBaseDto;
import com.europair.management.api.service.fleet.IAircraftBaseController;
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
public class AircraftBaseController implements IAircraftBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftBaseController.class);

    @Autowired
    private IAircraftBaseService aircraftBaseService;

    @Override
    public ResponseEntity<AircraftBaseDto> getAircraftBaseById(@NotNull Long aircraftId, @NotNull Long id) {
        LOGGER.debug("[AircraftBaseController] - Starting method [getAircraftBaseById] with input: getAircraftBaseById={}, id={}", aircraftId, id);
        AircraftBaseDto dto = aircraftBaseService.findById(aircraftId, id);
        LOGGER.debug("[AircraftBaseController] - Ending method [getAircraftBaseById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftBaseDto>> getAircraftBaseByFilter(@NotNull Long aircraftId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AircraftBaseController] - Starting method [getAircraftBaseByFilter] with input: aircraftId={}, pageable={}, reqParam={}", aircraftId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftBaseDto> dtoPage = aircraftBaseService.findAllPaginatedByFilter(aircraftId, pageable, criteria);
        LOGGER.debug("[AircraftBaseController] - Ending method [getAircraftBaseByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftBaseDto> saveAircraftBase(@NotNull Long aircraftId, @NotNull AircraftBaseDto aircraftBaseDto) {
        LOGGER.debug("[AircraftBaseController] - Starting method [saveAircraftBase] with input: aircraftId={}, aircraftBaseDto={}", aircraftId, aircraftBaseDto);
        final AircraftBaseDto dtoSaved = aircraftBaseService.saveAircraftBase(aircraftId, aircraftBaseDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftId, dtoSaved.getId())
                .toUri();

        LOGGER.debug("[AircraftBaseController] - Ending method [saveAircraftBase] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftBaseDto> updateAircraftBase(@NotNull Long aircraftId, @NotNull Long id, @NotNull AircraftBaseDto aircraftBaseDto) {
        LOGGER.debug("[AircraftBaseController] - Starting method [updateAircraftBase] with input: aircraftId={}, id={}, aircraftBaseDto={}", aircraftId, id, aircraftBaseDto);
        final AircraftBaseDto dtoSaved = aircraftBaseService.updateAircraftBase(aircraftId, id, aircraftBaseDto);
        LOGGER.debug("[AircraftBaseController] - Ending method [updateAircraftBase] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftBase(@NotNull Long aircraftId, @NotNull Long id) {
        LOGGER.debug("[AircraftBaseController] - Starting method [deleteAircraftBase] with input: aircraftId={}, id={}", aircraftId, id);
        aircraftBaseService.deleteAircraftBase(aircraftId, id);
        LOGGER.debug("[AircraftBaseController] - Ending method [deleteAircraftBase] with no return.");
        return ResponseEntity.noContent().build();
    }
}
