package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftTypeObservationDto;
import com.europair.management.api.service.fleet.IAircraftTypeObservationController;
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
public class AircraftTypeObservationController implements IAircraftTypeObservationController {

    @Autowired
    private IAircraftTypeObservationService aircraftTypeObservationService;

    @Override
    public ResponseEntity<AircraftTypeObservationDto> getAircraftTypeObservationById(@NotNull Long aircraftTypeId, @NotNull Long id) {
        AircraftTypeObservationDto dto = aircraftTypeObservationService.findById(aircraftTypeId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftTypeObservationDto>> getAircraftTypeObservationByFilter(@NotNull Long aircraftTypeId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftTypeObservationDto> dtoPage = aircraftTypeObservationService.findAllPaginatedByFilter(aircraftTypeId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftTypeObservationDto> saveAircraftTypeObservation(@NotNull Long aircraftTypeId, @NotNull AircraftTypeObservationDto aircraftTypeObservationDto) {
        final AircraftTypeObservationDto dtoSaved = aircraftTypeObservationService.saveAircraftTypeObservation(aircraftTypeId, aircraftTypeObservationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftTypeId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftTypeObservationDto> updateAircraftTypeObservation(@NotNull Long aircraftTypeId, @NotNull Long id, @NotNull AircraftTypeObservationDto aircraftTypeObservationDto) {
        final AircraftTypeObservationDto dtoSaved = aircraftTypeObservationService.updateAircraftTypeObservation(aircraftTypeId, id, aircraftTypeObservationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftTypeId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftTypeObservation(@NotNull Long aircraftTypeId, @NotNull Long id) {
        aircraftTypeObservationService.deleteAircraftTypeObservation(aircraftTypeId, id);
        return ResponseEntity.noContent().build();
    }
}
