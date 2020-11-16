package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightServiceDto;
import com.europair.management.api.service.flights.IFlightServiceController;
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
public class FlightServiceController implements IFlightServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceController.class);

    @Autowired
    private IFlightServiceService flightServiceService;

    @Override
    public ResponseEntity<Page<FlightServiceDto>> getAllFlightServicesPaginated(
            @NotNull Long fileId, @NotNull Long routeId, @NotNull Long flightId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[FlightServiceController] - Starting method [getAllFlightServicesPaginated] with input: fileId={}, routeId={}, flightId={}, pageable={}, reqParam={}",
                fileId, routeId, flightId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        Page<FlightServiceDto> pageResult = flightServiceService.findAllPaginatedByFilter(fileId, routeId, flightId, pageable, criteria);
        LOGGER.debug("[FlightServiceController] - Ending method [getAllFlightServicesPaginated] with return: {}", pageResult);
        return ResponseEntity.ok(pageResult);
    }

    @Override
    public ResponseEntity<FlightServiceDto> getFlightServiceById(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long flightId, @NotNull Long id) {
        LOGGER.debug("[FlightServiceController] - Starting method [getFlightServiceById] with input: fileId={}, routeId={}, flightId={}, id={}",
                fileId, routeId, flightId, id);
        FlightServiceDto resultDto = flightServiceService.findById(fileId, routeId, flightId, id);
        LOGGER.debug("[FlightServiceController] - Ending method [getFlightServiceById] with return: {}", resultDto);
        return ResponseEntity.ok(resultDto);
    }

    @Override
    public ResponseEntity<List<FlightServiceDto>> saveFlightService(@NotNull Long fileId, @NotNull Long routeId, @NotNull FlightServiceDto flightServiceDto) {
        LOGGER.debug("[FlightServiceController] - Starting method [saveFlightService] with input: fileId={}, routeId={}, flightServiceDto={}",
                fileId, routeId, flightServiceDto);
        List<FlightServiceDto> resultDto = flightServiceService.saveFlightService(fileId, routeId, flightServiceDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileId, routeId)
                .toUri();
        LOGGER.debug("[FlightServiceController] - Ending method [saveFlightService] with return: {}", resultDto);
        return ResponseEntity.created(location).body(resultDto);
    }

    @Override
    public ResponseEntity<?> updateFlightService(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long flightId, @NotNull Long id, @NotNull FlightServiceDto flightServiceDto) {
        LOGGER.debug("[FlightServiceController] - Starting method [updateFlightService] with input: fileId={}, routeId={}, id={}, flightServiceDto={}",
                fileId, routeId, id, flightServiceDto);
        flightServiceService.updateFlightService(fileId, routeId, flightId, id, flightServiceDto);
        LOGGER.debug("[FlightServiceController] - Ending method [updateFlightService] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteFlightService(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long flightId, @NotNull Long id) {
        LOGGER.debug("[FlightServiceController] - Starting method [deleteFlightService] with input: fileId={}, routeId={}, id={}",
                fileId, routeId, id);
        flightServiceService.deleteFlightService(fileId, routeId, flightId, id);
        LOGGER.debug("[FlightServiceController] - Ending method [deleteFlightService] with no return.");
        return ResponseEntity.noContent().build();
    }
}
