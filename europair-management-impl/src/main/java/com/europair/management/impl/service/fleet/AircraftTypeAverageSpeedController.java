package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftTypeAverageSpeedDto;
import com.europair.management.api.service.fleet.IAircraftTypeAverageSpeedController;
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
public class AircraftTypeAverageSpeedController implements IAircraftTypeAverageSpeedController {

    @Autowired
    private IAircraftTypeAverageSpeedService aircraftTypeAverageSpeedService;

    @Override
    public ResponseEntity<AircraftTypeAverageSpeedDto> getAircraftTypeAverageSpeedById(@NotNull Long aircraftTypeId, @NotNull Long id) {
        AircraftTypeAverageSpeedDto dto = aircraftTypeAverageSpeedService.findById(aircraftTypeId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftTypeAverageSpeedDto>> getAircraftTypeAverageSpeedByFilter(@NotNull Long aircraftTypeId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftTypeAverageSpeedDto> dtoPage = aircraftTypeAverageSpeedService.findAllPaginatedByFilter(aircraftTypeId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftTypeAverageSpeedDto> saveAircraftTypeAverageSpeed(@NotNull Long aircraftTypeId, @NotNull AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto) {
        final AircraftTypeAverageSpeedDto dtoSaved = aircraftTypeAverageSpeedService.saveAircraftTypeAverageSpeed(aircraftTypeId, aircraftTypeAverageSpeedDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftTypeId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftTypeAverageSpeedDto> updateAircraftTypeAverageSpeed(@NotNull Long aircraftTypeId, @NotNull Long id, @NotNull AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto) {
        final AircraftTypeAverageSpeedDto dtoSaved = aircraftTypeAverageSpeedService.updateAircraftTypeAverageSpeed(aircraftTypeId, id, aircraftTypeAverageSpeedDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftTypeId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftTypeAverageSpeed(@NotNull Long aircraftTypeId, @NotNull Long id) {
        aircraftTypeAverageSpeedService.deleteAircraftTypeAverageSpeed(aircraftTypeId, id);
        return ResponseEntity.noContent().build();
    }
}
