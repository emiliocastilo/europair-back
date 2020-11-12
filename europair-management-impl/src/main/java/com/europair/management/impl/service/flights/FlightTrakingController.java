package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.api.service.flights.IFlightTrakingController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
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
public class FlightTrakingController implements IFlightTrakingController {

    @Autowired
    private IFlightService flightService;

    public ResponseEntity<Page<FlightDTO>> getAllFlightsPaginated(final Pageable pageable, Map<String, String> reqParam) {
      CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
      final Page<FlightDTO> pageFlightsDTO = flightService.findAllPaginated(pageable, criteria);
      return ResponseEntity.ok().body(pageFlightsDTO);
    }

    public ResponseEntity<FlightDTO> getFlightById(@NotNull final Long id) {
      final FlightDTO flightDTO = flightService.findById(id);
      return ResponseEntity.ok(flightDTO);
    }

    
    public ResponseEntity<?> updateFlight(@NotNull final Long id, @NotNull final FlightDTO flightDTO) {
      flightService.updateFlight(id, flightDTO);
      return ResponseEntity.noContent().build();

    }
}
