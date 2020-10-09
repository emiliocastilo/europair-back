package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.api.integrations.office365.dto.ResponseContributionFlights;
import com.europair.management.api.integrations.office365.service.IOffice365Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
public class Office365Controller implements IOffice365Controller {

    @Autowired
    private IOffice365Service service;

    @Autowired
    private Office365Client office365Client;

    @Override
    public ResponseEntity<?> confirmOperation(@NotNull Long routeId, @NotNull Long contributionId) {
        service.confirmOperation(routeId, contributionId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ResponseContributionFlights> getEnabledFlightContributionInformation(@NotNull Long routeId, @NotNull Long contributionId, @NotNull Long flightId) {
        ResponseContributionFlights responseContributionFlights = service.getEnabledFlightContributionInformation(routeId, contributionId, flightId);
        return ResponseEntity.ok().body(responseContributionFlights);
    }

    @Override
    public ResponseEntity<String> sendEnabledFlightContributionInformation() {
        // TODO: substituir la url hardcodeada por un parametro en properties @Param
        office365Client.sendUriToEnabledFlightContributionInformation("http://localhost:8080/send/flight/contribution/test/collecturi");
        return null;
    }

    @Override
    public ResponseEntity<?> sendEnabledFlightContributionInformation(String fileUri) {
        System.out.println("Received URL : " + fileUri);
        return ResponseEntity.ok().body(fileUri);
    }

    @Override
    public ResponseEntity<List<PlanningFlightsDTO>> getFlightsInfo4Planning(@NotNull Long routeId,
                                                                            @NotNull Long contributionId,
                                                                            String actionType) {

        final List<PlanningFlightsDTO> planningFlightsDTOList = service.getPlanningFlightsInfo(routeId, contributionId, actionType);
        return ResponseEntity.ok(planningFlightsDTOList);
    }
}
