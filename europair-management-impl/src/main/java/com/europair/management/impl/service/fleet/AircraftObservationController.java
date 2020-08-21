package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftObservationDto;
import com.europair.management.api.service.fleet.IAircraftObservationController;
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
public class AircraftObservationController implements IAircraftObservationController {

    @Autowired
    private IAircraftObservationService aircraftObservationService;

    @Override
    public ResponseEntity<AircraftObservationDto> getAircraftObservationById(@NotNull Long aircraftId, @NotNull Long id) {
        AircraftObservationDto dto = aircraftObservationService.findById(aircraftId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftObservationDto>> getAircraftObservationByFilter(@NotNull Long aircraftId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftObservationDto> dtoPage = aircraftObservationService.findAllPaginatedByFilter(aircraftId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftObservationDto> saveAircraftObservation(@NotNull Long aircraftId, @NotNull AircraftObservationDto aircraftObservationDto) {
        final AircraftObservationDto dtoSaved = aircraftObservationService.saveAircraftObservation(aircraftId, aircraftObservationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftObservationDto> updateAircraftObservation(@NotNull Long aircraftId, @NotNull Long id, @NotNull AircraftObservationDto aircraftObservationDto) {
        final AircraftObservationDto dtoSaved = aircraftObservationService.updateAircraftObservation(aircraftId, id, aircraftObservationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftObservation(@NotNull Long aircraftId, @NotNull Long id) {
        aircraftObservationService.deleteAircraftObservation(aircraftId, id);
        return ResponseEntity.noContent().build();
    }
}
