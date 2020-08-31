package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
import com.europair.management.api.service.fleet.IAircraftSearchController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AircraftSearchController implements IAircraftSearchController {

    @Autowired
    private IAircraftSearchService aircraftSearchService;

    @Override
    public ResponseEntity<List<AircraftSearchResultDataDto>> searchAircraft(AircraftFilterDto filterDto) {
        return ResponseEntity.ok(aircraftSearchService.searchAircraft(filterDto));
    }
}
