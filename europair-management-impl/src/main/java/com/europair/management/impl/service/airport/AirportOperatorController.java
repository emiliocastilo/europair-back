package com.europair.management.impl.service.airport;


import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.api.service.airport.IAirportOperatorController;
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
public class AirportOperatorController implements IAirportOperatorController {

    @Autowired
    private IAirportOperatorService airportOperatorService;

    @Override
    public ResponseEntity<OperatorsAirportsDTO> getOperatorsAirportsById(@NotNull Long airportId, @NotNull Long id) {
        OperatorsAirportsDTO dto = airportOperatorService.findById(airportId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<OperatorsAirportsDTO>> getOperatorsAirportsByFilter(@NotNull Long airportId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<OperatorsAirportsDTO> dtoPage = airportOperatorService.findAllPaginatedByFilter(airportId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<OperatorsAirportsDTO> saveOperatorsAirports(@NotNull Long airportId, @NotNull OperatorsAirportsDTO operatorsAirportsDto) {
        final OperatorsAirportsDTO dtoSaved = airportOperatorService.saveOperatorsAirports(airportId, operatorsAirportsDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<OperatorsAirportsDTO> updateOperatorsAirports(@NotNull Long airportId, @NotNull Long id, @NotNull OperatorsAirportsDTO operatorsAirportsDto) {
        final OperatorsAirportsDTO dtoSaved = airportOperatorService.updateOperatorsAirports(airportId, id, operatorsAirportsDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteOperatorsAirports(@NotNull Long airportId, @NotNull Long id) {
        airportOperatorService.deleteOperatorsAirports(airportId, id);
        return ResponseEntity.noContent().build();
    }
}
