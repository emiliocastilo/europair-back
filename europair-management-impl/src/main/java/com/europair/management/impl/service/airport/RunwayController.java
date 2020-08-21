package com.europair.management.impl.service.airport;


import com.europair.management.api.dto.airport.RunwayDto;
import com.europair.management.api.service.airport.IRunwayController;
import com.europair.management.impl.service.airport.IRunwayService;
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
public class RunwayController implements IRunwayController {

    @Autowired
    private IRunwayService runwayService;

    @Override
    public ResponseEntity<RunwayDto> getRunwayById(@NotNull Long airportId, @NotNull Long id) {
        RunwayDto dto = runwayService.findById(airportId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<RunwayDto>> getRunwayByFilter(@NotNull Long airportId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<RunwayDto> dtoPage = runwayService.findAllPaginatedByFilter(airportId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<RunwayDto> saveRunway(@NotNull Long airportId, @NotNull RunwayDto runwayDto) {
        final RunwayDto dtoSaved = runwayService.saveRunway(airportId, runwayDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<RunwayDto> updateRunway(@NotNull Long airportId, @NotNull Long id, @NotNull RunwayDto runwayDto) {
        final RunwayDto dtoSaved = runwayService.updateRunway(airportId, id, runwayDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteRunway(@NotNull Long airportId, @NotNull Long id) {
        runwayService.deleteRunway(airportId, id);
        return ResponseEntity.noContent().build();
    }
}
