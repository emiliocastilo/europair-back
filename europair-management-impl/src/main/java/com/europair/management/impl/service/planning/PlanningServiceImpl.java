package com.europair.management.impl.service.planning;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.dto.integration.PlanningFlightsDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.UTCEnum;
import com.europair.management.impl.service.flights.IFlightService;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.repository.IFlightRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

public class PlanningServiceImpl implements IPlanningService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<PlanningFlightsDTO> getPlanningFlightsInfo(Long fileId, Long routeId) {

        List<PlanningFlightsDTO> planningFlightsDTOList = new ArrayList<>();

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));

        for (Route routeRotation : route.getRotations()) {
            for (Flight flight: routeRotation.getFlights()) {

                PlanningFlightsDTO planningFlightsDTO = new PlanningFlightsDTO();

                flight.getDepartureTime().

                        airportRepository.findFirstByIataCode(flight.getOrigin()).get().getTimeZone(); // Aeropuerto

                Utils.TimeConverter.getLocalTimeInOtherUTC(UTCEnum.ONE, flight.getDepartureTime().toString(), UTCEnum /*del aeropuerto*/);

                planningFlightsDTOList.add(planningFlightsDTO);
            }
        }




        return planningFlightsDTOList;

    }
}
