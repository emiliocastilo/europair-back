package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.api.service.flights.IFlightTrakingController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@Slf4j
public class FlightTrakingController implements IFlightTrakingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceController.class);

    @Autowired
    private IFlightService flightService;

    public ResponseEntity<Page<FlightDTO>> getAllFlightsPaginated(final Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[FlightTrackingController] - Starting method [getAllFlightsPaginated] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<FlightDTO> pageFlightsDTO = flightService.findAllPaginated(pageable, criteria);
        LOGGER.debug("[FlightTrackingController] - Ending method [getAllFlightsPaginated] with return: {}", pageFlightsDTO);
        return ResponseEntity.ok().body(pageFlightsDTO);
    }

    public ResponseEntity<FlightDTO> getFlightById(@NotNull final Long id) {
        LOGGER.debug("[FlightTrackingController] - Starting method [getFlightById] with input: id={}", id);
        final FlightDTO flightDTO = flightService.findById(id);
        LOGGER.debug("[FlightTrackingController] - Ending method [getFlightById] with return: {}", flightDTO);
        return ResponseEntity.ok(flightDTO);
    }


    public ResponseEntity<?> updateFlight(@NotNull final Long id, @NotNull final FlightDTO flightDTO) {
        LOGGER.debug("[FlightTrackingController] - Starting method [updateFlight] with input: id={}, flightDTO={}", id, flightDTO);
        flightService.updateFlight(id, flightDTO);
        LOGGER.debug("[FlightTrackingController] - Ending method [updateFlight] with no return.");
        return ResponseEntity.noContent().build();
    }
}
