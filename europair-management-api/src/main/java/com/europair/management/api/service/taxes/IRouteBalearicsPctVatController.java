package com.europair.management.api.service.taxes;

import com.europair.management.api.dto.taxes.RouteBalearicsPctVatDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@RequestMapping("/routeBalearicsPctVat")
public interface IRouteBalearicsPctVatController {

  /**
   * <p>
   * Retrieves a route with Balearics percentage liable to VAT from origin and destination airports
   * </p>
   *
   * @param originAirportId origin airport identifier.
   * @param destinationAirportId destination airport identifier.
   * @return route with Balearics percentage liable to VAT
   */
  @GetMapping("/")
  @Operation(description = "Retrieve file data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<RouteBalearicsPctVatDTO> getRouteBalearicsPercentageByOriginAndDestination(
      @Parameter(description = "Origin airport identifier") @NotNull @RequestParam final Long originAirportId,
      @Parameter(description = "Destination airport identifier") @NotNull @RequestParam final Long destinationAirportId);

}
