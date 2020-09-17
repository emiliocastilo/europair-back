package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightServiceDto;
import com.europair.management.impl.mappers.flights.IFlightServiceMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.flights.entity.FlightService;
import com.europair.management.rest.model.flights.repository.FlightServiceRepository;
import com.europair.management.rest.model.flights.repository.IFlightRepository;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class FlightServiceServiceImpl implements IFlightServiceService {

    private final String FLIGHT_ID_FILTER = "flight.id";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private FlightServiceRepository flightServiceRepository;


    @Override
    public Page<FlightServiceDto> findAllPaginatedByFilter(Long fileId, Long routeId, Long flightId, Pageable pageable, CoreCriteria criteria) {
        validatePathIds(fileId, routeId, flightId);
        Utils.addCriteriaIfNotExists(criteria, FLIGHT_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(flightId));

        return flightServiceRepository.findFlightServiceByCriteria(criteria, pageable)
                .map(IFlightServiceMapper.INSTANCE::toDto);
    }

    @Override
    public FlightServiceDto findById(Long fileId, Long routeId, Long flightId, Long id) {
        validatePathIds(fileId, routeId, flightId);
        return IFlightServiceMapper.INSTANCE.toDto(flightServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FlightService not found with id: " + id)));
    }

    @Override
    public FlightServiceDto saveFlightService(Long fileId, Long routeId, Long flightId, FlightServiceDto flightServiceDto) {
        validatePathIds(fileId, routeId, flightId);
        if (flightServiceDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New FlightService expected. Identifier %s got", flightServiceDto.getId()));
        }

        // Set relationship ids
        flightServiceDto.setFlightId(flightId);

        FlightService flightService = IFlightServiceMapper.INSTANCE.toEntity(flightServiceDto);
        flightService = flightServiceRepository.save(flightService);

        return IFlightServiceMapper.INSTANCE.toDto(flightService);
    }

    @Override
    public FlightServiceDto updateFlightService(Long fileId, Long routeId, Long flightId, Long id, FlightServiceDto flightServiceDto) {
        validatePathIds(fileId, routeId, flightId);
        FlightService flightService = flightServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FlightService not found with id: " + id));
        IFlightServiceMapper.INSTANCE.updateFromDto(flightServiceDto, flightService);
        flightService = flightServiceRepository.save(flightService);

        return IFlightServiceMapper.INSTANCE.toDto(flightService);
    }

    @Override
    public void deleteFlightService(Long fileId, Long routeId, Long flightId, Long id) {
        validatePathIds(fileId, routeId, flightId);
        if (!flightServiceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FlightService not found with id: " + id);
        }
        flightServiceRepository.deleteById(id);
    }


    private void validatePathIds(final Long fileId, final Long routeId, final Long flightId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
        if (!routeRepository.existsById(routeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId);
        }
        if (!flightRepository.existsById(flightId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + flightId);
        }
    }

}
