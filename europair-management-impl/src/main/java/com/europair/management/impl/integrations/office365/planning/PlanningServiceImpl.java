package com.europair.management.impl.integrations.office365.planning;

import com.europair.management.api.enums.ContributionStates;
import com.europair.management.api.integrations.office365.dto.FileSharingInfoDTO;
import com.europair.management.api.integrations.office365.dto.FlightSharingInfoDTO;
import com.europair.management.api.integrations.office365.dto.PlanningFlightsDTO;
import com.europair.management.impl.service.conversions.ConversionService;
import com.europair.management.impl.util.DistanceSpeedUtils;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.repository.FlightRepository;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.operators.repository.OperatorRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanningServiceImpl implements IPlanningService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<PlanningFlightsDTO> getPlanningFlightsInfo(Long routeId, String actionType) {

        List<PlanningFlightsDTO> planningFlightsDTOList = new ArrayList<>();

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));

        File file = route.getFile();

        for (Route routeRotation : route.getRotations()) {
            for (Flight flight: routeRotation.getFlights()) {
                PlanningFlightsDTO planningFlightsDTO = getPlanningFlightsDTO(actionType, route, file, flight);
                planningFlightsDTOList.add(planningFlightsDTO);
            }
        }

        return planningFlightsDTOList;
    }

    @Override
    public PlanningFlightsDTO getPlanningFlightsDTO(String actionType, Route route, File file, Flight flight) {
        PlanningFlightsDTO planningFlightsDTO = new PlanningFlightsDTO();

        planningFlightsDTO.setActionType(actionType);

        // file info
        planningFlightsDTO.setFileSharingInfoDTO(new FileSharingInfoDTO());

        planningFlightsDTO.getFileSharingInfoDTO().setCode(file.getCode());
        planningFlightsDTO.getFileSharingInfoDTO().setDescription(file.getDescription());
        planningFlightsDTO.getFileSharingInfoDTO().setFileUrl(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/files/" + file.getId())
                        .build().toUri().toString()
        );

        // flight info
        planningFlightsDTO.setFlightSharingInfoDTO(new FlightSharingInfoDTO());

        planningFlightsDTO.getFlightSharingInfoDTO().setOperationType(file.getOperationType());

        // origin airport (IATA | ICAO | Name)
        Airport originAirport = airportRepository.findFirstByIataCode(flight.getOrigin()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Origin airport not found with IATA code: " + flight.getOrigin()));
        planningFlightsDTO.getFlightSharingInfoDTO().setOriginAirport(
                originAirport.getIataCode() + " | " + originAirport.getIcaoCode() + " | " + originAirport.getName());

        // destination airport (IATA | ICAO | Name)
        Airport destinationAirport = airportRepository.findFirstByIataCode(flight.getDestination()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destination airport not found with IATA code: " + flight.getDestination()));
        planningFlightsDTO.getFlightSharingInfoDTO().setDestinationAirport(
                destinationAirport.getIataCode() + " | " + destinationAirport.getIcaoCode() + " | " + destinationAirport.getName());

        // Take aircraft info from contribution
        Contribution contribution = null;
        for ( Contribution contributionAux: route.getContributions()) {
            if (contributionAux.getContributionState().equals(ContributionStates.CONFIRMED)) {
                contribution = contributionAux;
            }
        }

        // flight dates (UTC)
        planningFlightsDTO.getFlightSharingInfoDTO().setStartDate(flight.getDepartureTime());
        // TODO: uncomment this lines. this was commited to the repository to share the code
        //DistanceSpeedUtils distanceSpeedUtils = Utils.calculateDistanceAndSpeed(conversionService, contribution.getAircraft().getAircraftType(), originAirport, destinationAirport);
        //planningFlightsDTO.getFlightSharingInfoDTO().setEndDate(flight.getDepartureTime().plusHours(distanceSpeedUtils.getTimeInHours().longValue()) );

        // TODO: transformar la fecha que obtenemos a fecha y hora local

        // flight dates (local)
        planningFlightsDTO.getFlightSharingInfoDTO().setLocalStartDate(
                planningFlightsDTO.getFlightSharingInfoDTO().getStartDate()
                        .plusHours(originAirport.getTimeZone().getHours())
                        .plusMinutes(originAirport.getTimeZone().getMinutes())
        );

        ZonedDateTime.now();
        //TODO: Utils.TimeConverter.getLocalTimeInOtherUTC(UTCEnum.ONE, routeRotation.getStartDate().toString(), originAirport.getTimeZone());

        planningFlightsDTO.getFlightSharingInfoDTO().setLocalEndDate(
                planningFlightsDTO.getFlightSharingInfoDTO().getEndDate()
                        .plusHours(destinationAirport.getTimeZone().getHours())
                        .plusMinutes(destinationAirport.getTimeZone().getMinutes())
        );

        planningFlightsDTO.getFlightSharingInfoDTO().setFlightNumber("");

        final Long contributionId = contribution.getOperatorId();
        Operator operator = operatorRepository.findById(contributionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Operator not found with id: " + contributionId)
        );
        planningFlightsDTO.getFlightSharingInfoDTO().setOperator(operator.getIataCode() + " | " + operator.getIcaoCode() + " | " + operator.getName());
        planningFlightsDTO.getFlightSharingInfoDTO().setPlateNumber(contribution.getAircraft().getPlateNumber());

        planningFlightsDTO.getFlightSharingInfoDTO().setClient(file.getClient().getCode() + " | " + file.getClient().getName());
        planningFlightsDTO.getFlightSharingInfoDTO().setPaxTotalNumber(flight.getSeatsF() + flight.getSeatsC() + flight.getSeatsY());
        planningFlightsDTO.getFlightSharingInfoDTO().setBedsNumber(flight.getBeds());
        planningFlightsDTO.getFlightSharingInfoDTO().setStretchersNumber(flight.getStretchers());

        //TODO: carga será un valor numérico, ¿de dónde se obtiene?
        planningFlightsDTO.getFlightSharingInfoDTO().setCharge("0");
        return planningFlightsDTO;
    }
}
