package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.ResponseContributionFlights;
import com.europair.management.api.integrations.office365.service.IOffice365Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
public class Office365Controller implements IOffice365Controller {

    @Autowired
    private IOffice365Service service;

    @Override
    public ResponseEntity<?> confirmOperation(@NotNull Long routeId, @NotNull Long contributionId) {
        service.confirmOperation(routeId, contributionId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> getEnabledFlightContributionInformation(@NotNull Long routeId, @NotNull Long contributionId, @NotNull Long flightId) {
        ResponseContributionFlights responseContributionFlights = service.getEnabledFlightContributionInformation(routeId, contributionId, flightId);
        return ResponseEntity.ok().body(responseContributionFlights);
    }

    @Override
    public ResponseEntity<?> testEnabledFlightContributionInformation() {


        return null;
    }
}
