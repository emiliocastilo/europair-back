package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.service.airport.IAirportController;
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
public class AirportController implements IAirportController {

    @Autowired
    private IAirportService airportService;

    @Override
    public ResponseEntity<AirportDto> getAirportById(@NotNull Long id) {
        AirportDto dto = airportService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<AirportDto>> getAirportByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AirportDto> dtoPage = airportService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<AirportDto> saveAirport(@NotNull AirportDto airportDto) {
        final AirportDto dtoSaved = airportService.saveAirport(airportDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AirportDto> updateAirport(@NotNull Long id, @NotNull AirportDto airportDto) {
        final AirportDto dtoSaved = airportService.updateAirport(id, airportDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAirport(@NotNull Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.noContent().build();
    }
}
