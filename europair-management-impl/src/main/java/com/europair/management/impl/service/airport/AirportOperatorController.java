package com.europair.management.impl.service.airport;


import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.api.service.airport.IAirportOperatorController;
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
public class AirportOperatorController implements IAirportOperatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirportOperatorController.class);

    @Autowired
    private IAirportOperatorService airportOperatorService;

    @Override
    public ResponseEntity<OperatorsAirportsDTO> getOperatorsAirportsById(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[AirportOperatorController] - Starting method [getOperatorsAirportsById] with input: airportId={}, id={}", airportId, id);
        OperatorsAirportsDTO dto = airportOperatorService.findById(airportId, id);
        LOGGER.debug("[AirportOperatorController] - Ending method [getOperatorsAirportsById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<OperatorsAirportsDTO>> getOperatorsAirportsByFilter(@NotNull Long airportId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AirportOperatorController] - Starting method [getOperatorsAirportsByFilter] with input: airportId={}, pageable={}, reqParams={}",
                airportId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<OperatorsAirportsDTO> dtoPage = airportOperatorService.findAllPaginatedByFilter(airportId, pageable, criteria);
        LOGGER.debug("[AirportOperatorController] - Ending method [getOperatorsAirportsByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<OperatorsAirportsDTO> saveOperatorsAirports(@NotNull Long airportId, @NotNull OperatorsAirportsDTO operatorsAirportsDto) {
        LOGGER.debug("[AirportOperatorController] - Starting method [saveOperatorsAirports] with input: airportId={}, operatorsAirportsDto={}",
                airportId, operatorsAirportsDto);
        final OperatorsAirportsDTO dtoSaved = airportOperatorService.saveOperatorsAirports(airportId, operatorsAirportsDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        LOGGER.debug("[AirportOperatorController] - Ending method [saveOperatorsAirports] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<OperatorsAirportsDTO> updateOperatorsAirports(@NotNull Long airportId, @NotNull Long id, @NotNull OperatorsAirportsDTO operatorsAirportsDto) {
        LOGGER.debug("[AirportOperatorController] - Starting method [updateOperatorsAirports] with input: airportId={}, id={}, operatorsAirportsDto={}",
                airportId, id, operatorsAirportsDto);
        final OperatorsAirportsDTO dtoSaved = airportOperatorService.updateOperatorsAirports(airportId, id, operatorsAirportsDto);
        LOGGER.debug("[AirportOperatorController] - Ending method [updateOperatorsAirports] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteOperatorsAirports(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[AirportOperatorController] - Starting method [deleteOperatorsAirports] with input: airportId={}, id={}", airportId, id);
        airportOperatorService.deleteOperatorsAirports(airportId, id);
        LOGGER.debug("[AirportOperatorController] - Ending method [deleteOperatorsAirports] with no return.");
        return ResponseEntity.noContent().build();
    }
}
