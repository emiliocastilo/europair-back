package com.europair.management.api.service.fleet;


import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/aircraft-search")
public interface IAircraftSearchController {

    /**
     * <p>
     * Search aircraft that match the filters
     * </p>
     *
     * @param filterDto Filter object
     * @return List of aircraft
     */
    @GetMapping
    @Operation(description = "Aircraft list that matches the filters", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<List<AircraftSearchResultDataDto>> searchAircraft(
            @Parameter(description = "Objects with filter parameters") AircraftFilterDto filterDto);

    @GetMapping("/test")
    ResponseEntity<List<AircraftDto>> test(AircraftFilterDto filterDto);

}
