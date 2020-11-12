package com.europair.management.api.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.ConfirmedOperationDto;
import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.api.integrations.office365.dto.ResponseContributionFlights;
import com.europair.management.api.integrations.office365.dto.SimplePlanningDTO;
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

@RequestMapping(value = {"/integration/office", "/external/integration/office"})
public interface IOffice365Controller {


    /**
     * <p>
     * Confirms operation and send operation data to the office_365 endpoint.
     * </p>
     *
     * @param routeId        Unique route identifier.
     * @param contributionId Unique contribution identifier.
     * @return No content
     */
    @GetMapping("/confirmation/{routeId}/{contributionId}")
    @Operation(description = "Sends operation confirmed data to an url of the Office365 systems", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> confirmOperation(
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId);

    /**
     * <p>
     * Sends url to get the confirmed operation data.
     * </p>
     *
     * @param routeId        Unique route identifier.
     * @param contributionId Unique contribution identifier.
     * @return No content
     */
    @GetMapping("/send/confirmation/{routeId}/{contributionId}")
    @Operation(description = "Send an url to the Office365 systems to indicate that the information is enabled, and can be retrieved from the url",
            security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<String> sendConfirmedOperation(
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId);

    /**
     * <p>
     * This operation allows to office_365 power app to retrieve all the information related with the confirmed operation
     * </p>
     *
     * @param routeId        Unique route identifier.
     * @param contributionId Unique contribution identifier.
     * @return No content
     */
    @GetMapping("/get/confirmation/{routeId}/{contributionId}")
    @Operation(description = "Retrieves confirmed operation data for the route and contribution selected.",
            security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ConfirmedOperationDto> getConfirmedOperation(
            @Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
            @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId);

    /**
     * This opperation allows to office_365 power app to retrieve all the information related with Flight&Contribution
     *
     * @param contributionId
     * @return
     */
    @GetMapping("/get/flight/contribution/{contributionId}")
    @Operation(description = "Send an url to the Office365 systems to indicate that the information is enabled, and can be retrieved from the url", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ResponseContributionFlights> getEnabledFlightContributionInformation(@Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId);

    /**
     * This opperation sends to office_365 the signal and the path who needs to know that the information is enabled and ready to consume
     * @return
     */
    @GetMapping("/send/flight/contribution")
    ResponseEntity<String> sendEnabledFlightContributionInformation(final Long contributionId, final Long flightId);


    @GetMapping("/planning-old")
    @Operation(description = "Flight info list for planning", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<List<PlanningFlightsDTO>> getFlightsInfo4Planning(
            @Parameter(description = "file id") @RequestParam @NotNull final Long fileId);

    @GetMapping("/planning")
    @Operation(description = "Info list for planning", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<List<SimplePlanningDTO>> getPlanningData(
            @Parameter(description = "file id") @RequestParam @NotNull final Long fileId);

}
