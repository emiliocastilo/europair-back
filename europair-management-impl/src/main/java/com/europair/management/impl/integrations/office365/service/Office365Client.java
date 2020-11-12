package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.ConfirmedOperationDto;
import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.api.integrations.office365.dto.ResponseSendPlanningFlightsDTO;
import com.europair.management.api.integrations.office365.dto.SimplePlaningFlightDTO;
import com.europair.management.api.integrations.office365.dto.SimplePlanningDTO;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@FeignClient(name = "office365", url = "${europair.integration.office365.host}")
public interface Office365Client {

    @RequestMapping(method = RequestMethod.POST, value = "${europair.integration.office365.endpoints.confirm}")
    void sendConfirmedOperationData(@RequestBody ConfirmedOperationDto confirmedOperation);

    @RequestMapping(method = RequestMethod.POST, value = "${europair.integration.office365.endpoints.confirm-uri}")
    void sendConfirmedOperationUri(@RequestParam(name = "operationUri") String operationUri);

    @RequestMapping(method = RequestMethod.GET, value = "${europair.integration.office365.endpoints.flights}")
    void sendUriToEnabledFlightContributionInformation(@RequestParam(name = "fileUri") String fileUri);

    @RequestMapping(method = RequestMethod.POST, value = "${europair.integration.office365.endpoints.planning}")
    void sendPlaningFlightsDTOList(
            @RequestParam(value="api-version") String apiVersion,
            @RequestParam(value="sp") String sp,
            @RequestParam(value="sv") String sv,
            @RequestParam(value="sig") String sig,
            @RequestBody List<PlanningFlightsDTO> planningFlightsDTOList);


    //?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=2U-Uo5w2-zy2NNYCgxSgX1Wqoj_tvvWKnyhDJeEQtbM
    @RequestMapping(method = RequestMethod.POST, value = "${europair.integration.office365.endpoints.planning}")
    ResponseSendPlanningFlightsDTO sendPlaningFlightsDTO(
            @RequestParam(value="api-version") String apiVersion,
            @RequestParam(value="sp") String sp,
            @RequestParam(value="sv") String sv,
            @RequestParam(value="sig") String sig,
            @RequestBody SimplePlaningFlightDTO planningFlightsDTO);

    //?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=2U-Uo5w2-zy2NNYCgxSgX1Wqoj_tvvWKnyhDJeEQtbM
    @RequestMapping(method = RequestMethod.POST, value = "${europair.integration.office365.endpoints.planning}")
    ResponseSendPlanningFlightsDTO sendPlanning(
            @RequestParam(value="api-version") String apiVersion,
            @RequestParam(value="sp") String sp,
            @RequestParam(value="sv") String sv,
            @RequestParam(value="sig") String sig,
            @RequestBody SimplePlanningDTO simplePlanningDTO);
}
