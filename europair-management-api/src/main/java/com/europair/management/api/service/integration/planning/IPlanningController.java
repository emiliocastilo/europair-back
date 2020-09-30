package com.europair.management.api.service.integration.planning;

import com.europair.management.api.dto.integration.PlanningFlightsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/planning")
public interface IPlanningController {

    @GetMapping("")
    @Operation(description = "Flight info list for planning", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<List<PlanningFlightsDTO>> getFlightsInfo4Planning(
            @Parameter(description = "file id") @RequestParam final Long fileId,
            @Parameter(description = "route id") @RequestParam final Long routeId
    );

}
