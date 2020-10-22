package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.ConfirmedOperationDto;
import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "office365", url = "${europair.integration.office365.host}")
public interface Office365Client {

    @RequestMapping(method = RequestMethod.POST, value = "${europair.integration.office365.endpoints.confirm}")
    void sendConfirmedOperationData(@RequestBody ConfirmedOperationDto confirmedOperation);

    @RequestMapping(method = RequestMethod.POST, value = "${europair.integration.office365.endpoints.confirm-uri}")
    void sendConfirmedOperationUri(@RequestParam(name = "operationUri") String operationUri);

    @RequestMapping(method = RequestMethod.GET, value = "${europair.integration.office365.endpoints.flights}")
    void sendUriToEnabledFlightContributionInformation(@RequestParam(name = "fileUri") String fileUri);

    @RequestMapping(method = RequestMethod.POST, value = "${europair.integration.office365.endpoints.planning")
    void sendPlaningFlightsDTOList(List<PlanningFlightsDTO> planningFlightsDTOList);

    @RequestMapping(method = RequestMethod.POST, value = "${europair.integration.office365.endpoints.planning")
    void sendPlaningFlightsDTO(PlanningFlightsDTO planningFlightsDTO);
}
