package com.europair.management.impl.service.taxes;

import com.europair.management.api.dto.taxes.RouteBalearicsPctVatDTO;
import com.europair.management.api.service.taxes.IRouteBalearicsPctVatController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
public class RouteBalearicsPctVatController implements IRouteBalearicsPctVatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteBalearicsPctVatController.class);

    @Autowired
    private IRouteBalearicsPctVatService routeBalearicsPctVatService;

    @Override
    public ResponseEntity<RouteBalearicsPctVatDTO> getRouteBalearicsPercentageByOriginAndDestination(
            @NotNull @RequestParam final Long originAirportId, @NotNull @RequestParam final Long destinationAirportId) {
        LOGGER.debug("[RouteBalearicsPctVatController] - Starting method [getRouteBalearicsPercentageByOriginAndDestination] with input: originAirportId={}, destinationAirportId={},",
                originAirportId, destinationAirportId);
        final RouteBalearicsPctVatDTO routeBalearicsPctVatDTO =
                routeBalearicsPctVatService.findByOriginAndDestinationWithInverseSearch(originAirportId, destinationAirportId);
        LOGGER.debug("[RouteBalearicsPctVatController] - Ending method [getRouteBalearicsPercentageByOriginAndDestination] with return: {}", routeBalearicsPctVatDTO);
        return ResponseEntity.ok(routeBalearicsPctVatDTO);
    }

}
