package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.ResponseContributionFlights;

public interface IOffice365Service {

    void confirmOperation(Long routeId, Long contributionId);

    ResponseContributionFlights getEnabledFlightContributionInformation(Long routeId, Long contributionId, Long flightId);
}
