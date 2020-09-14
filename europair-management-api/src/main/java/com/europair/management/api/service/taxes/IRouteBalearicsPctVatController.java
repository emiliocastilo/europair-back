package com.europair.management.api.service.taxes;

import com.europair.management.api.dto.taxes.RouteBalearicsPctVatDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;

@RequestMapping("/routeBalearicsPctVat")
public interface IRouteBalearicsPctVatController {

  /**
   * <p>
   * Retrieves a route with Balearics percentage liable to VAT from origin and destination airports
   * </p>
   *
   * @param originId origin airport identifier.
   * @param destinationId destination airport identifier.
   * @return route with Balearics percentage liable to VAT
   */
  @GetMapping("/{id}")
  @Operation(description = "Retrieve file data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<RouteBalearicsPctVatDTO> getRouteBalearicsPercentageByOriginAndDestination(
      @Parameter(description = "Origin airport identifier") @NotNull @PathVariable final Long originId,
      @Parameter(description = "Destination airport identifier") @NotNull @PathVariable final Long destinationId);

}
