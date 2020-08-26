package com.europair.management.impl.service.airport;


import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.api.service.airport.IAirportRegionController;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private IAirportRegionService airportRegionService;

    @Override
    public ResponseEntity<RegionDTO> getRegionById(@NotNull Long airportId, @NotNull Long id) {
        RegionDTO dto = airportRegionService.findById(airportId, id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<RegionDTO>> getRegionByFilter(@NotNull Long airportId, Pageable pageable) {
        final Page<RegionDTO> dtoPage = airportRegionService.findAllPaginatedByFilter(airportId, pageable);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<RegionDTO> saveRegion(@NotNull Long airportId, @NotNull RegionDTO regionDto) {
        final RegionDTO dtoSaved = airportRegionService.saveRegionAirport(airportId, regionDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteRegion(@NotNull Long airportId, @NotNull Long id) {
        airportRegionService.deleteRegionAirport(airportId, id);
        return ResponseEntity.noContent().build();
    }
}
