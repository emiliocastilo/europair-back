package com.europair.management.impl.service.taxes;

import com.europair.management.api.dto.taxes.RouteBalearicsPctVatDTO;
import com.europair.management.api.service.taxes.IRouteBalearicsPctVatController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
public class RouteBalearicsPctVatController implements IRouteBalearicsPctVatController {

  @Autowired
  private IRouteBalearicsPctVatService routeBalearicsPctVatService;

  @Override
  public ResponseEntity<RouteBalearicsPctVatDTO> getRouteBalearicsPercentageByOriginAndDestination(
        @NotNull final Long originAirportId, @NotNull final Long destinationAirportId) {

    final RouteBalearicsPctVatDTO routeBalearicsPctVatDTO =
        routeBalearicsPctVatService.findByOriginAndDestinationWithInverseSearch(originAirportId, destinationAirportId);
    return ResponseEntity.ok(routeBalearicsPctVatDTO);
  }

}
