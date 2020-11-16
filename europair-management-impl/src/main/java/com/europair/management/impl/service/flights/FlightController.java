package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.api.service.flights.IFlightController;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FlightController implements IFlightController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private IFlightService flightService;

    public ResponseEntity<Page<FlightDTO>> getAllFlightsPaginated(@NotNull Long fileId, @NotNull Long routeId, final Pageable pageable,
                                                                  Map<String, String> reqParam) {
        LOGGER.debug("[FlightController] - Starting method [getAllFlightsPaginated] with input: fileId={}, routeId={}, pageable={}, reqParam={}",
                fileId, routeId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<FlightDTO> pageFlightsDTO = flightService.findAllPaginated(fileId, routeId, pageable, criteria);
        LOGGER.debug("[FlightController] - Ending method [getAllFlightsPaginated] with return: {}", pageFlightsDTO);
        return ResponseEntity.ok().body(pageFlightsDTO);
    }

    public ResponseEntity<FlightDTO> getFlightById(@NotNull Long fileId, @NotNull Long routeId, @NotNull final Long id) {
        LOGGER.debug("[FlightController] - Starting method [getFlightById] with input: id={}, fileId={}, routeId={}", id, fileId, routeId);
        final FlightDTO flightDTO = flightService.findById(fileId, routeId, id);
        LOGGER.debug("[FlightController] - Ending method [getFlightById] with return: {}", flightDTO);
        return ResponseEntity.ok(flightDTO);
    }

    public ResponseEntity<FlightDTO> saveFlight(@NotNull Long fileId, @NotNull Long routeId, @NotNull final FlightDTO flightDTO) {
        LOGGER.debug("[FlightController] - Starting method [saveFlight] with input: fileId={}, routeId={}, flightDTO={}", fileId, routeId, flightDTO);
        final FlightDTO flightDTOSaved = flightService.saveFlight(fileId, routeId, flightDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(flightDTOSaved.getId())
                .toUri();
        LOGGER.debug("[FlightController] - Ending method [saveFlight] with return: {}", flightDTOSaved);
        return ResponseEntity.created(location).body(flightDTOSaved);
    }

    public ResponseEntity<?> updateFlight(@NotNull Long fileId, @NotNull Long routeId, @NotNull final Long id,
                                          @NotNull final FlightDTO flightDTO) {
        LOGGER.debug("[FlightController] - Starting method [updateFlight] with input: fileId={}, routeId={}, id={}, flightDTO={}", fileId, routeId, id, flightDTO);
        flightService.updateFlight(fileId, routeId, id, flightDTO);
        LOGGER.debug("[FlightController] - Ending method [updateFlight] with no return.");
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> deleteFlight(@NotNull Long fileId, @NotNull Long routeId, @NotNull final Long id) {
        LOGGER.debug("[FlightController] - Starting method [deleteFlight] with input: id={}, fileId={}, routeId={}", id, fileId, routeId);
        flightService.deleteFlight(fileId, routeId, id);
        LOGGER.debug("[FlightController] - Ending method [deleteFlight] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> updateFlightsOrder(@NotNull Long fileId, @NotNull Long routeId, @NotNull List<FlightDTO> flights) {
        LOGGER.debug("[FlightController] - Starting method [updateFlightsOrder] with input: fileId={}, routeId={}, flights={}", fileId, routeId, flights);
        flightService.updateFlightsOrder(fileId, routeId, flights);
        LOGGER.debug("[FlightController] - Ending method [updateFlightsOrder] with no return.");
        return ResponseEntity.noContent().build();
    }
}
