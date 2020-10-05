package com.europair.management.impl.integrations.office365.planning;

import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.api.service.integration.planning.IPlanningController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlanningController implements IPlanningController {

    @Autowired
    private IPlanningService planningService;

    @Override
    public ResponseEntity<List<PlanningFlightsDTO>> getFlightsInfo4Planning(Long routeId, String actionType) {

        final List<PlanningFlightsDTO> planningFlightsDTOList = planningService.getPlanningFlightsInfo(routeId, actionType);
        return ResponseEntity.ok(planningFlightsDTOList);
    }
}
