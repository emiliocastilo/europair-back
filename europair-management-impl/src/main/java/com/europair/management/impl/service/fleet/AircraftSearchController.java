package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
import com.europair.management.api.service.fleet.IAircraftSearchController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AircraftSearchController implements IAircraftSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftSearchController.class);

    @Autowired
    private IAircraftSearchService aircraftSearchService;

    @Override
    public ResponseEntity<List<AircraftSearchResultDataDto>> searchAircraft(AircraftFilterDto filterDto) {
        LOGGER.debug("[AircraftSearchController] - Starting method [searchAircraft] with input: filterDto={}", filterDto);
        List<AircraftSearchResultDataDto> res = aircraftSearchService.searchAircraft(filterDto);
        LOGGER.debug("[AircraftSearchController] - Ending method [searchAircraft] with return: {}", res);
        return ResponseEntity.ok(res);
    }

}
