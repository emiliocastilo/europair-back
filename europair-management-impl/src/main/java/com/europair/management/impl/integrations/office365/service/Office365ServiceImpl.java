package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.*;
import com.europair.management.impl.integrations.office365.mappers.IOffice365Mapper;
import com.europair.management.impl.integrations.office365.planning.IPlanningService;
import com.europair.management.impl.service.conversions.ConversionService;
import com.europair.management.impl.util.DistanceSpeedUtils;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.entity.FlightService;
import com.europair.management.rest.model.flights.repository.FlightRepository;
import com.europair.management.rest.model.flights.repository.FlightServiceRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.entity.RouteAirport;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class Office365ServiceImpl implements IOffice365Service {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private FlightServiceRepository flightServiceRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private IPlanningService iPlanningService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private Office365Client office365Client;


    @Override
    public void confirmOperation(Long routeId, Long contributionId) {

        Route route = routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));

        Contribution contribution = contributionRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + contributionId));

        ConfirmedOperationDto confirmedOperationDto = mapConfirmedOperation(route, contribution);

        // Send data
        office365Client.sendConfirmedOperationData(confirmedOperationDto);
    }

    @Override
    public ResponseContributionFlights getEnabledFlightContributionInformation(Long routeId, Long contributionId, Long flightId) {

        ResponseContributionFlights responseContributionFlights = new ResponseContributionFlights();

        // precondiciones
        Contribution contribution = this.contributionRepository.findById(contributionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + contributionId));

        Aircraft aircraft = this.aircraftRepository.findById(contribution.getAircraftId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found with id: " + contribution.getAircraftId()));

        // first step: planningFlightsDTO -> fileSharingInfoDTO, flightSharingInfoDTO
        Route route = this.routeRepository.findById(routeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));
        File file = route.getFile();
        Flight flight = this.flightRepository.findById(flightId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found with id: " + flightId));
        PlanningFlightsDTO planningFlightsDTO = this.iPlanningService.getPlanningFlightsDTO(null,route,file,flight);

        // seccond step: aircraftSharingDTO
        AircraftSharingDTO aircraftSharingDTO = new AircraftSharingDTO();

        for (AircraftBase aircraftBase: aircraft.getBases()) {
            if (null != aircraftBase.getMainBase()) {
                aircraftSharingDTO.setBaseCode(aircraftBase.getAirport().getIataCode());
                aircraftSharingDTO.setBaseName(aircraftBase.getAirport().getName());
            }
        }
        if (null != aircraft.getAircraftType()) {
            aircraftSharingDTO.setTypeCode(aircraft.getAircraftType().getCode());
            aircraftSharingDTO.setTypeName(aircraft.getAircraftType().getDescription());

            if( null != aircraft.getAircraftType().getCategory()) {
                aircraftSharingDTO.setCategoryCode(aircraft.getAircraftType().getCategory().getCode());
                aircraftSharingDTO.setCategoryName(aircraft.getAircraftType().getCategory().getName());
            }

            if (null != aircraft.getAircraftType().getSubcategory()) {
                aircraftSharingDTO.setSubcategoryCode(aircraft.getAircraftType().getSubcategory().getCode());
                aircraftSharingDTO.setSubcategoryName(aircraft.getAircraftType().getSubcategory().getName());
            }
        }
        // third step: operatorSharingDTO;
        OperatorSharingDTO operatorSharingDTO = new OperatorSharingDTO();

        if ( null != contribution.getOperator()) {
            operatorSharingDTO.setIataCode(contribution.getOperator().getIataCode());
            operatorSharingDTO.setIcaoCode(contribution.getOperator().getIcaoCode());
            operatorSharingDTO.setName(contribution.getOperator().getName());
        }

        // union de datos
        responseContributionFlights.setAircraftSharingDTO(aircraftSharingDTO);
        responseContributionFlights.setOperatorSharingDTO(operatorSharingDTO);
        responseContributionFlights.setPlanningFlightsDTO(planningFlightsDTO);

        return responseContributionFlights;
    }


    // Mapping functions

    private ConfirmedOperationDto mapConfirmedOperation(final Route route, final Contribution contribution) {
        Map<String, Airport> airportIataMap = route.getAirports().stream()
                .map(RouteAirport::getAirport)
                .distinct()
                .collect(Collectors.toMap(Airport::getIataCode, airport -> airport));
        final List<DistanceSpeedUtils> dsDataList = new ArrayList<>();

        ConfirmedOperationDto dto = new ConfirmedOperationDto();
        dto.setFileInfo(IOffice365Mapper.INSTANCE.mapFile(route));
        dto.setFlightsInfo(mapFlightsWithServices(route, contribution, airportIataMap, dsDataList));
        dto.setContributionInfo(IOffice365Mapper.INSTANCE.mapContribution(contribution));
        dto.setObservations(route.getFile().getObservation());

        return dto;
    }

    private List<FlightExtendedInfoDto> mapFlightsWithServices(final Route route, final Contribution contribution,
                                                               final Map<String, Airport> airportIataMap,
                                                               final List<DistanceSpeedUtils> dsDataList) {
        return route.getFlights().stream().map(flight -> {
            Airport origin = airportIataMap.get(flight.getOrigin());
            Airport destination = airportIataMap.get(flight.getDestination());

            FlightExtendedInfoDto dto = new FlightExtendedInfoDto();
            dto.setOperationType(route.getFile().getOperationType());
            dto.setPaxTotalNumber(flight.getSeatsC() + flight.getSeatsF() + flight.getSeatsY());
            dto.setBedsNumber(flight.getBeds());
            dto.setStretchersNumber(flight.getStretchers());
            dto.setOriginAirport(origin.getIataCode() + " | " + origin.getName());
            dto.setDestinationAirport(destination.getIataCode() + " | " + destination.getName());
            dto.setStartDate(flight.getDepartureTime());
            dto.setPlateNumber(contribution.getAircraft().getPlateNumber());
            dto.setOperator(contribution.getAircraft().getOperator().getName());
            dto.setClient(route.getFile().getClient().getCode() + " | " + route.getFile().getClient().getName());
            dto.setCharge(null); // ToDo: de donde lo sacamos?
            dto.setFlightNumber(null);// ToDo: de donde lo sacamos?

            dto.setServices(mapFlightServices(flight.getId()));

            // Calculate arrivalTime
            DistanceSpeedUtils dsData;
            Optional<DistanceSpeedUtils> optionalData = dsDataList.stream().filter(dsu -> dsu.getOriginId().equals(origin.getId())
                    && dsu.getDestinationId().equals(destination.getId())
                    && dsu.getAircraftTypeId().equals(contribution.getAircraft().getAircraftType().getId()))
                    .findFirst();
            if (optionalData.isPresent()) {
                dsData = optionalData.get();
            } else {
                dsData = Utils.calculateDistanceAndSpeed(conversionService, contribution.getAircraft().getAircraftType(), origin, destination);
                dsDataList.add(dsData);
            }
            dto.setEndDate(dsData.getTimeInHours() != null ?
                    flight.getDepartureTime().plusHours(dsData.getTimeInHours().longValue()) : null);


            return dto;
        }).collect(Collectors.toList());
    }

    private List<FlightServiceDataDto> mapFlightServices(final Long flightId) {
        List<FlightService> flightServices = flightServiceRepository.findAllByFlightId(flightId);
        return CollectionUtils.isEmpty(flightServices) ? null : flightServices.stream()
                .map(IOffice365Mapper.INSTANCE::mapFlightService)
                .collect(Collectors.toList());
    }

}
