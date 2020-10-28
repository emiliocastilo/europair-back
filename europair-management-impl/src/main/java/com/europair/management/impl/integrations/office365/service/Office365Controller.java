package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.ConfirmedOperationDto;
import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.api.integrations.office365.dto.ResponseContributionFlights;
import com.europair.management.api.integrations.office365.dto.SimplePlaningFlightDTO;
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


    private static final String API_VERSION = "2016-06-01";
    private static final String SP = "%2Ftriggers%2Fmanual%2Frun";
    private static final String SV = "1.0";
    private static final String SIG = "2U-Uo5w2-zy2NNYCgxSgX1Wqoj_tvvWKnyhDJeEQtbM";


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
    public ResponseEntity<ResponseContributionFlights> getEnabledFlightContributionInformation( @NotNull Long flightId, @NotNull Long contributionId ) {
        ResponseContributionFlights responseContributionFlights = service.getEnabledFlightContributionInformation( contributionId, flightId);
        return ResponseEntity.ok().body(responseContributionFlights);
    }

    @Override
    public ResponseEntity<String> sendEnabledFlightContributionInformation(Long contributionId, Long flightId) {
        String dataUrl = baseUrl + String.format("/get/flight/contribution/%s/%s",contributionId,flightId);
//        office365Client.sendUriToEnabledFlightContributionInformation(dataUrl);
        return ResponseEntity.ok(dataUrl);
    }

    @Override
    public ResponseEntity<List<PlanningFlightsDTO>> getFlightsInfo4Planning(@NotNull Long routeId,
                                                                            @NotNull Long contributionId,
                                                                            String actionType) {

        final List<PlanningFlightsDTO> planningFlightsDTOList = service.getPlanningFlightsInfo(routeId, contributionId, actionType);

        office365Client.sendPlaningFlightsDTOList(API_VERSION, SP, SV, SIG, planningFlightsDTOList);
        //sendOneByOnePlanningFlightDTOToOffice365(planningFlightsDTOList);

        return ResponseEntity.ok(planningFlightsDTOList);
    }

    /**
     * Sends a list of PlanningFlightDTO element by element
     * // TODO: if must implement a retry policy must be here.
     *
     * @param planningFlightsDTOList
     */
    private void sendOneByOnePlanningFlightDTOToOffice365(List<PlanningFlightsDTO> planningFlightsDTOList) {
        for( PlanningFlightsDTO planningFlightsDTO : planningFlightsDTOList){

            if ( null != planningFlightsDTO.getFileSharingInfoDTO() && null != planningFlightsDTO.getFlightSharingInfoDTO() ) {

                SimplePlaningFlightDTO simplePlaningFlightDTO = new SimplePlaningFlightDTO();

                simplePlaningFlightDTO.setTitle("PLANNING :" + planningFlightsDTO.getFileSharingInfoDTO().getCode());
                simplePlaningFlightDTO.setClient(planningFlightsDTO.getFlightSharingInfoDTO().getClient());
                simplePlaningFlightDTO.setDescription(planningFlightsDTO.getFileSharingInfoDTO().getDescription());
                simplePlaningFlightDTO.setStartDate(planningFlightsDTO.getFlightSharingInfoDTO().getStartDate());
                simplePlaningFlightDTO.setEndDate(planningFlightsDTO.getFlightSharingInfoDTO().getEndDate());

                office365Client.sendPlaningFlightsDTO(API_VERSION, SP, SV, SIG, planningFlightsDTO);
            }
        }
    }
}
