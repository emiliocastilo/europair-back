package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.api.integrations.office365.dto.ResponseContributionFlights;

import java.util.List;

public interface IOffice365Service {

    void confirmOperation(Long routeId, Long contributionId);

    ResponseContributionFlights getEnabledFlightContributionInformation(Long routeId, Long contributionId, Long flightId);

    List<PlanningFlightsDTO> getPlanningFlightsInfo(Long routeId, Long contributionId, String actionType);
}
