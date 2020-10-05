package com.europair.management.impl.integrations.office365.planning;

import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;

import java.util.List;

public interface IPlanningService {

    List<PlanningFlightsDTO> getPlanningFlightsInfo(Long routeId, String actionType);

}
