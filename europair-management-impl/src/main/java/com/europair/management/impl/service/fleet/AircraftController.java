package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.service.fleet.IAircraftController;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class AircraftController implements IAircraftController {

    @Autowired
    private IAircraftService aircraftService;

    public ResponseEntity<AircraftDto> getAircraftById(@NotNull final Long id) {
        final AircraftDto aircraftDto = aircraftService.findById(id);
        return ResponseEntity.ok(aircraftDto);
    }

    public ResponseEntity<Page<AircraftDto>> getAircraftByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftDto> aircraftDtoPage = aircraftService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(aircraftDtoPage);
    }

    public ResponseEntity<AircraftDto> saveAircraft(@NotNull final AircraftDto aircraftDto) {

        final AircraftDto aircraftDtoSaved = aircraftService.saveAircraft(aircraftDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftDtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(aircraftDtoSaved);

    }

    public ResponseEntity<AircraftDto> updateAircraft(@NotNull final Long id, @NotNull final AircraftDto aircraftDto) {

        final AircraftDto aircraftDtoSaved = aircraftService.updateAircraft(id, aircraftDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aircraftDtoSaved.getId())
                .toUri();

        return ResponseEntity.ok().body(aircraftDtoSaved);

    }

    public ResponseEntity<?> deleteAircraft(@NotNull final Long id) {

        aircraftService.deleteAircraft(id);
        return ResponseEntity.noContent().build();

    }


    @Override
    public ResponseEntity<List<AircraftDto>> searchAircraft(AircraftFilterDto filterDto) {

        double origLat = 39.55;
        double origLon = 2.73333333;
        double destLat = 47.4647222;
        double destLon = 8.54916667;

        Utils.calculateDistance_test(origLat, origLon, destLat, destLon);

        return ResponseEntity.ok(new ArrayList<>());
//        return ResponseEntity.ok(aircraftService.searchAircraft(filterDto));
    }
}
