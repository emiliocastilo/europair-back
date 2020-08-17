package com.europair.management.impl.service.airport;


import com.europair.management.api.dto.airport.AirportObservationDto;
import com.europair.management.api.service.airport.IAirportObservationController;
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
public class AirportObservationController implements IAirportObservationController {

    @Autowired
    private IAirportObservationService airportObservationService;

    @Override
    public ResponseEntity<AirportObservationDto> getAirportObservationById(@NotNull Long airportId, @NotNull Long id) {
        AirportObservationDto dto = airportObservationService.findById(airportId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AirportObservationDto>> getAirportObservationByFilter(@NotNull Long airportId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AirportObservationDto> dtoPage = airportObservationService.findAllPaginatedByFilter(airportId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AirportObservationDto> saveAirportObservation(@NotNull Long airportId, @NotNull AirportObservationDto airportObservationDto) {
        final AirportObservationDto dtoSaved = airportObservationService.saveAirportObservation(airportId, airportObservationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AirportObservationDto> updateAirportObservation(@NotNull Long airportId, @NotNull Long id, @NotNull AirportObservationDto airportObservationDto) {
        final AirportObservationDto dtoSaved = airportObservationService.updateAirportObservation(airportId, id, airportObservationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAirportObservation(@NotNull Long airportId, @NotNull Long id) {
        airportObservationService.deleteAirportObservation(airportId, id);
        return ResponseEntity.noContent().build();
    }
}
