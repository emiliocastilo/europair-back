package com.europair.management.api.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.api.integrations.office365.dto.ResponseContributionFlights;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/integration/office")
public interface IOffice365Controller {


    /**
     * <p>
     * Confirms operation and send operation data to another system.
     * </p>
     *
     * @param routeId        Unique route identifier.
     * @param contributionId Unique contribution identifier.
     * @return No content
     */
    @GetMapping("/confirmation/{routeId}/{contributionId}")
    @Operation(description = "Retrieve master airport data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> confirmOperation(
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId);

    /**
     * This opperation allows to office_365 power app to retrieve all the information related with Flight&Contribution
     *
     * @param routeId
     * @param contributionId
     * @param flightId
     * @return
     */
    @GetMapping("/get/flight/contribution")
    @Operation(description = "Send an url to the Office365 systems to indicate that the information is enabled, and can be retrieved from the url", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ResponseContributionFlights> getEnabledFlightContributionInformation(@Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
                                                                                        @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId,
                                                                                        @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long flightId);

    /**
     * This opperation sends to office_365 the signal and the path who needs to know that the information is enabled and ready to consume
     * @return
     */
    @GetMapping("/send/flight/contribution/test")
    ResponseEntity<String> sendEnabledFlightContributionInformation();

    @GetMapping("/get/flight/contribution/test/collecturi")
    ResponseEntity<?> sendEnabledFlightContributionInformation(@Param("fileUri") String fileUri);

    @GetMapping("/planning")
    @Operation(description = "Flight info list for planning", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<List<PlanningFlightsDTO>> getFlightsInfo4Planning(
            @Parameter(description = "route id") @RequestParam @NotNull final Long routeId,
            @Parameter(description = "Contribution identifier") @RequestParam @NotNull final Long contributionId,
            @Parameter(description = "action type") @RequestParam @NotNull final String actionType);

}
