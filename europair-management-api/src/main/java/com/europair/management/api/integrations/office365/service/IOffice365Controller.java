package com.europair.management.api.integrations.office365.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;

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


    @GetMapping("/get/flight/contribution")
    @Operation(description = "Send an url to the Office365 systems to indicate that the information is enabled, and can be retrieved from the url", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> getEnabledFlightContributionInformation(@Parameter(description = "Route identifier") @NotNull @PathVariable final Long routeId,
                                                               @Parameter(description = "Contribution identifier") @NotNull @PathVariable final Long contributionId,
                                                               @Parameter(description = "Flight identifier") @NotNull @PathVariable final Long flightId);

    @GetMapping("/get/flight/contribution/test")
    ResponseEntity<?> testEnabledFlightContributionInformation();

}
