package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeDto;
import com.europair.management.api.service.fleet.IAircraftTypeController;
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
public class AircraftTypeController implements IAircraftTypeController {

    @Autowired
    private IAircraftTypeService aircraftTypeService;

    @Override
    public ResponseEntity<AircraftTypeDto> getAircraftTypeById(@NotNull Long id) {
        AircraftTypeDto dto = aircraftTypeService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AircraftTypeDto>> getAircraftTypeByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftTypeDto> dtoPage = aircraftTypeService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AircraftTypeDto> saveAircraftType(@NotNull AircraftTypeDto aircraftTypeDto) {
        final AircraftTypeDto dtoSaved = aircraftTypeService.saveAircraftType(aircraftTypeDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftTypeDto> updateAircraftType(@NotNull Long id, @NotNull AircraftTypeDto aircraftTypeDto) {
        final AircraftTypeDto dtoSaved = aircraftTypeService.updateAircraftType(id, aircraftTypeDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftType(@NotNull Long id) {
        aircraftTypeService.deleteAircraftType(id);
        return ResponseEntity.noContent().build();
    }

}
