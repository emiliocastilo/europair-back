package com.europair.management.impl.service.airport;


import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.api.service.airport.IAirportRegionController;
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

@RestController
@Slf4j
public class AirportRegionController implements IAirportRegionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirportRegionController.class);

    @Autowired
    private IAirportRegionService airportRegionService;

    @Override
    public ResponseEntity<RegionDTO> getRegionById(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[AirportRegionController] - Starting method [getRegionById] with input: airportId={}, id={}", airportId, id);
        RegionDTO dto = airportRegionService.findById(airportId, id);
        LOGGER.debug("[AirportRegionController] - Ending method [getRegionById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<RegionDTO>> getRegionByFilter(@NotNull Long airportId, Pageable pageable) {
        LOGGER.debug("[AirportRegionController] - Starting method [getRegionByFilter] with input: airportId={}, pageable={}", airportId, pageable);
        final Page<RegionDTO> dtoPage = airportRegionService.findAllPaginatedByFilter(airportId, pageable);
        LOGGER.debug("[AirportRegionController] - Ending method [getRegionByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<RegionDTO> saveRegion(@NotNull Long airportId, @NotNull RegionDTO regionDto) {
        LOGGER.debug("[AirportRegionController] - Starting method [saveRegion] with input: airportId={}, regionDto={}", airportId, regionDto);
        final RegionDTO dtoSaved = airportRegionService.saveRegionAirport(airportId, regionDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        LOGGER.debug("[AirportRegionController] - Ending method [saveRegion] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteRegion(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[AirportRegionController] - Starting method [deleteRegion] with input: airportId={}, id={}", airportId, id);
        airportRegionService.deleteRegionAirport(airportId, id);
        LOGGER.debug("[AirportRegionController] - Ending method [deleteRegion] with no return");
        return ResponseEntity.noContent().build();
    }
}
