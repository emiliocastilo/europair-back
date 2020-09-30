package com.europair.management.impl.service.planning;

import com.europair.management.api.dto.integration.PlanningFlightsDTO;

import java.util.List;

public interface IPlanningService {

    List<PlanningFlightsDTO> getPlanningFlightsInfo(Long fileId, Long routeId);

}
