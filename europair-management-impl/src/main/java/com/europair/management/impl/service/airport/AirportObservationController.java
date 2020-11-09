package com.europair.management.impl.service.airport;


import com.europair.management.api.dto.airport.AirportObservationDto;
import com.europair.management.api.service.airport.IAirportObservationController;
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
public class AirportObservationController implements IAirportObservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirportObservationController.class);

    @Autowired
    private IAirportObservationService airportObservationService;

    @Override
    public ResponseEntity<AirportObservationDto> getAirportObservationById(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[AirportObservationController] - Starting method [getAirportObservationById] with input: airportId={}, id={}", airportId, id);
        AirportObservationDto dto = airportObservationService.findById(airportId, id);
        LOGGER.debug("[AirportObservationController] - Ending method [getAirportObservationById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AirportObservationDto>> getAirportObservationByFilter(@NotNull Long airportId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AirportObservationController] - Starting method [getAirportObservationByFilter] with input: airportId={}, pageable={}, reqParam={}",
                airportId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AirportObservationDto> dtoPage = airportObservationService.findAllPaginatedByFilter(airportId, pageable, criteria);
        LOGGER.debug("[AirportObservationController] - Ending method [getAirportObservationByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AirportObservationDto> saveAirportObservation(@NotNull Long airportId, @NotNull AirportObservationDto airportObservationDto) {
        LOGGER.debug("[AirportObservationController] - Starting method [saveAirportObservation] with input: airportId={}, airportObservationDto={}",
                airportId, airportObservationDto);
        final AirportObservationDto dtoSaved = airportObservationService.saveAirportObservation(airportId, airportObservationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        LOGGER.debug("[AirportObservationController] - Ending method [saveAirportObservation] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AirportObservationDto> updateAirportObservation(@NotNull Long airportId, @NotNull Long id, @NotNull AirportObservationDto airportObservationDto) {
        LOGGER.debug("[AirportObservationController] - Starting method [updateAirportObservation] with input: airportId={}, airportObservationDto={}",
                airportId, airportObservationDto);
        final AirportObservationDto dtoSaved = airportObservationService.updateAirportObservation(airportId, id, airportObservationDto);

        LOGGER.debug("[AirportObservationController] - Ending method [updateAirportObservation] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAirportObservation(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[AirportObservationController] - Starting method [deleteAirportObservation] with input: airportId={}, id={}",
                airportId, id);
        airportObservationService.deleteAirportObservation(airportId, id);
        LOGGER.debug("[AirportObservationController] - Ending method [deleteAirportObservation] with no return.");
        return ResponseEntity.noContent().build();
    }
}
