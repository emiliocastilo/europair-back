package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.*;
import com.europair.management.api.integrations.office365.service.IOffice365Controller;
import com.europair.management.rest.model.routes.entity.Route;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
public class Office365Controller implements IOffice365Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Office365Controller.class);

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
    public ResponseEntity<ResponseContributionFlights> getEnabledFlightContributionInformation( @NotNull Long contributionId ) {
        ResponseContributionFlights responseContributionFlights = service.getEnabledFlightContributionInformation( contributionId);
        return ResponseEntity.ok().body(responseContributionFlights);
    }

    @Override
    public ResponseEntity<String> sendEnabledFlightContributionInformation(Long contributionId, Long flightId) {
        String dataUrl = baseUrl + String.format("/get/flight/contribution/%s/%s",contributionId,flightId);
//        office365Client.sendUriToEnabledFlightContributionInformation(dataUrl);
        return ResponseEntity.ok(dataUrl);
    }

    @Override
    public ResponseEntity<List<PlanningFlightsDTO>> getFlightsInfo4Planning(@NotNull Long fileId) {

        // first step: retrieve all the routes in state WON with his contribution
        List<MinimalRouteInfoToSendThePlanningFlightsDTO> routeListToSendPlaning = this.service.getAllRoutesToSendPlanningFlights(fileId);

        for (MinimalRouteInfoToSendThePlanningFlightsDTO infoRouteToSendPlaning : routeListToSendPlaning){
            final List<PlanningFlightsDTO> planningFlightsDTOList =
                    service.getPlanningFlightsInfo( infoRouteToSendPlaning.getRouteId(),
                            infoRouteToSendPlaning.getContributionId());

            //office365Client.sendPlaningFlightsDTOList(API_VERSION, SP, SV, SIG, planningFlightsDTOList);
            sendOneByOnePlanningFlightDTOToOffice365(planningFlightsDTOList);
        }

        return ResponseEntity.ok().build();
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
                // TODO: pening meeting to talk about this and what client must be here
                // hardcoded client because patterson power app uses this default client
                simplePlaningFlightDTO.setClient("adminbroker@europair.es");
                /*simplePlaningFlightDTO.setClient(planningFlightsDTO.getFlightSharingInfoDTO().getClient());*/
                simplePlaningFlightDTO.setDescription(planningFlightsDTO.getFileSharingInfoDTO().getDescription());
                simplePlaningFlightDTO.setStartDate(planningFlightsDTO.getFlightSharingInfoDTO().getStartDate().toLocalDate());
                simplePlaningFlightDTO.setEndDate((null != planningFlightsDTO.getFlightSharingInfoDTO().getEndDate() ? planningFlightsDTO.getFlightSharingInfoDTO().getEndDate().toLocalDate() : planningFlightsDTO.getFlightSharingInfoDTO().getStartDate().toLocalDate()));

                try {
                    ResponseSendPlanningFlightsDTO responseSendPlanningFlightsDTO = office365Client.sendPlaningFlightsDTO(API_VERSION, SP, SV, SIG, simplePlaningFlightDTO);
                    LOGGER.debug(responseSendPlanningFlightsDTO.toString());

                    // TODO: cuando nos confirmen hay que marcar el vuelo como enviado
                    // marcar en bbdd los vuelos como que han sido enviados
                    /*planningFlightsDTO.getFlightSharingInfoDTO().getFlightId();*/


                } catch (FeignException ex){
                    LOGGER.error("Propagate feign exception", ex);
                }
            }
        }
    }
}
