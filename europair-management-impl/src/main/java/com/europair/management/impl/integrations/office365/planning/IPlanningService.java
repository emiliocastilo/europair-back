package com.europair.management.impl.integrations.office365.planning;

import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.routes.entity.Route;

import java.util.List;

public interface IPlanningService {

    List<PlanningFlightsDTO> getPlanningFlightsInfo(Long routeId, String actionType);

    PlanningFlightsDTO getPlanningFlightsDTO(String actionType, Route route, File file, Flight flight);

}
