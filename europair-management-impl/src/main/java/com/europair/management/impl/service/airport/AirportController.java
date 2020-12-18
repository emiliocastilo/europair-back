package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.service.airport.IAirportController;
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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;
import java.util.Set;

@RestController
@Slf4j
public class AirportController implements IAirportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirportController.class);

    @Autowired
    private IAirportService airportService;

    @Override
    public ResponseEntity<AirportDto> getAirportById(@NotNull Long id) {
        LOGGER.debug("[AirportController] - Starting method [getAirportById] with input: id={}", id);
        AirportDto dto = airportService.findById(id);
        LOGGER.debug("[AirportController] - Ending method [getAirportById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AirportDto>> getAirportByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AirportController] - Starting method [getAirportByFilter] with input: pageable={}, reqParams={}",
                pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AirportDto> dtoPage = airportService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[AirportController] - Ending method [getAirportByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AirportDto> saveAirport(@NotNull AirportDto airportDto) {
        LOGGER.debug("[AirportController] - Starting method [saveAirport] with input: airportDto={}", airportDto);
        final AirportDto dtoSaved = airportService.saveAirport(airportDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        LOGGER.debug("[AirportController] - Ending method [saveAirport] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AirportDto> updateAirport(@NotNull Long id, @NotNull AirportDto airportDto) {
        LOGGER.debug("[AirportController] - Starting method [updateAirport] with input: airportDto={}", airportDto);
        final AirportDto dtoSaved = airportService.updateAirport(id, airportDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        LOGGER.debug("[AirportController] - Ending method [updateAirport] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAirport(@NotNull Long id) {
        LOGGER.debug("[AirportController] - Starting method [deleteAirport] with input: id={}", id);
        airportService.deleteAirport(id);
        LOGGER.debug("[AirportController] - Ending method [deleteAirport] with no return");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> reactivateAirports(@NotEmpty Set<Long> airportIds) {
        LOGGER.debug("[AirportController] - Starting method [reactivateAirports] with input: airportIds={}", airportIds);
        airportService.reactivateAirports(airportIds);
        LOGGER.debug("[AirportController] - Ending method [reactivateAirports] with no return");
        return ResponseEntity.noContent().build();
    }
}
