package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.api.service.flights.IFlightController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@Slf4j
public class FlightController implements IFlightController {

    @Autowired
    private IFlightService flightService;

    public ResponseEntity<Page<FlightDTO>> getAllFlightsPaginated(@NotNull Long fileId, @NotNull Long routeId, final Pageable pageable) {
      final Page<FlightDTO> pageFlightsDTO = flightService.findAllPaginated(fileId, routeId, pageable);
      return ResponseEntity.ok().body(pageFlightsDTO);

    }

    public ResponseEntity<FlightDTO> getFlightById(@NotNull Long fileId, @NotNull Long routeId, @NotNull final Long id) {
      final FlightDTO flightDTO = flightService.findById(fileId, routeId, id);
      return ResponseEntity.ok(flightDTO);
    }

    public ResponseEntity<FlightDTO> saveFlight(@NotNull Long fileId, @NotNull Long routeId, @NotNull final FlightDTO flightDTO) {

      final FlightDTO flightDTOSaved = flightService.saveFlight(fileId, routeId, flightDTO);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(flightDTOSaved.getId())
        .toUri();

      return ResponseEntity.created(location).body(flightDTOSaved);

    }

    public ResponseEntity<FlightDTO> updateFlight(@NotNull Long fileId, @NotNull Long routeId, @NotNull final Long id,
                                                  @NotNull final FlightDTO flightDTO) {

      final FlightDTO flightDTOUpdated = flightService.updateFlight(fileId, routeId, id, flightDTO);
      return ResponseEntity.ok().body(flightDTOUpdated);

    }

    public ResponseEntity<?> deleteFlight(@NotNull Long fileId, @NotNull Long routeId, @NotNull final Long id) {

      flightService.deleteFlight(fileId, routeId, id);
      return ResponseEntity.noContent().build();

    }

}
