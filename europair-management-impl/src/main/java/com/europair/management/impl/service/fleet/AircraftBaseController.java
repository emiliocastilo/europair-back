package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftBaseDto;
import com.europair.management.api.service.fleet.IAircraftBaseController;
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
public class AircraftBaseController implements IAircraftBaseController {

    @Autowired
    private IAircraftBaseService aircraftBaseService;

    @Override
    public ResponseEntity<AircraftBaseDto> getAircraftBaseById(@NotNull Long aircraftId, @NotNull Long id) {
        AircraftBaseDto dto = aircraftBaseService.findById(aircraftId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftBaseDto>> getAircraftBaseByFilter(@NotNull Long aircraftId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftBaseDto> dtoPage = aircraftBaseService.findAllPaginatedByFilter(aircraftId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftBaseDto> saveAircraftBase(@NotNull Long aircraftId, @NotNull AircraftBaseDto aircraftBaseDto) {
        final AircraftBaseDto dtoSaved = aircraftBaseService.saveAircraftBase(aircraftId, aircraftBaseDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftBaseDto> updateAircraftBase(@NotNull Long aircraftId, @NotNull Long id, @NotNull AircraftBaseDto aircraftBaseDto) {
        final AircraftBaseDto dtoSaved = aircraftBaseService.updateAircraftBase(aircraftId, id, aircraftBaseDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftBase(@NotNull Long aircraftId, @NotNull Long id) {
        aircraftBaseService.deleteAircraftBase(aircraftId, id);
        return ResponseEntity.noContent().build();
    }
}
