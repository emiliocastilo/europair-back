package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.ConfirmedOperationDto;
import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.api.integrations.office365.dto.ResponseContributionFlights;
import com.europair.management.api.integrations.office365.service.IOffice365Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${europair.web.base.url}")
    private String baseUrl;


    @Override
    public ResponseEntity<?> confirmOperation(@NotNull Long routeId, @NotNull Long contributionId) {
        ConfirmedOperationDto confirmedOperationDto = service.getConfirmedOperationData(routeId, contributionId);
        // Send data
        office365Client.sendConfirmedOperationData(confirmedOperationDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<String> sendConfirmedOperation(@NotNull Long routeId, @NotNull Long contributionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl).append("/get/confirmation/").append(routeId).append("/").append(contributionId);
        office365Client.sendConfirmedOperationUri(sb.toString());
        return ResponseEntity.ok(sb.toString());
    }

    @Override
    public ResponseEntity<ConfirmedOperationDto> getConfirmedOperation(@NotNull Long routeId, @NotNull Long contributionId) {
        ConfirmedOperationDto confirmedOperationDto = service.getConfirmedOperationData(routeId, contributionId);
        return ResponseEntity.ok(confirmedOperationDto);
    }

    @Override
    public ResponseEntity<ResponseContributionFlights> getEnabledFlightContributionInformation(@NotNull Long routeId, @NotNull Long flightId, @NotNull Long contributionId ) {
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
    public ResponseEntity<?> testOfSendEnabledFlightContributionInformation(String fileUri) {
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
