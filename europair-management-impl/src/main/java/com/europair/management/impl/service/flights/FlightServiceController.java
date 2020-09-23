package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightServiceDto;
import com.europair.management.api.service.flights.IFlightServiceController;
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
import java.util.Map;

@RestController
@Slf4j
public class FlightServiceController implements IFlightServiceController {

    @Autowired
    private IFlightServiceService flightServiceService;

    @Override
    public ResponseEntity<Page<FlightServiceDto>> getAllFlightServicesPaginated(
            @NotNull Long fileId, @NotNull Long routeId, @NotNull Long flightId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        Page<FlightServiceDto> pageResult = flightServiceService.findAllPaginatedByFilter(fileId, routeId, flightId, pageable, criteria);
        return ResponseEntity.ok(pageResult);
    }

    @Override
    public ResponseEntity<FlightServiceDto> getFlightServiceById(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long flightId, @NotNull Long id) {
        FlightServiceDto resultDto = flightServiceService.findById(fileId, routeId, flightId, id);
        return ResponseEntity.ok(resultDto);
    }

    @Override
    public ResponseEntity<FlightServiceDto> saveFlightService(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long flightId, @NotNull FlightServiceDto flightServiceDto) {
        FlightServiceDto resultDto = flightServiceService.saveFlightService(fileId, routeId, flightId, flightServiceDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileId, routeId, flightId, resultDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(resultDto);
    }

    @Override
    public ResponseEntity<?> updateFlightService(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long flightId, @NotNull Long id, @NotNull FlightServiceDto flightServiceDto) {
        flightServiceService.updateFlightService(fileId, routeId, flightId, id, flightServiceDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteFlightService(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long flightId, @NotNull Long id) {
        flightServiceService.deleteFlightService(fileId, routeId, flightId, id);
        return ResponseEntity.noContent().build();
    }
}
