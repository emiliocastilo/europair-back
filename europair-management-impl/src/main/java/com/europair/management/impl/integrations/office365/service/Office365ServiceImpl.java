package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.ConfirmedOperationDto;
import com.europair.management.api.integrations.office365.dto.FlightExtendedInfoDto;
import com.europair.management.api.integrations.office365.dto.FlightServiceDataDto;
import com.europair.management.impl.integrations.office365.mappers.IOffice365Mapper;
import com.europair.management.impl.util.DistanceSpeedUtils;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.flights.entity.FlightService;
import com.europair.management.rest.model.flights.repository.FlightServiceRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void confirmOperation(Long routeId, Long contributionId) {

        Route route = routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));

        Contribution contribution = contributionRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + contributionId));

        ConfirmedOperationDto confirmedOperationDto = mapConfirmedOperation(route, contribution);

        // ToDo: send data

    }


    // Mapping functions

    private ConfirmedOperationDto mapConfirmedOperation(final Route route, final Contribution contribution) {
        ConfirmedOperationDto dto = new ConfirmedOperationDto();
        dto.setFileInfo(IOffice365Mapper.INSTANCE.mapFile(route));
        dto.setFlightsInfo(mapFlightsWithServices(route, contribution));
        dto.setContributionInfo(IOffice365Mapper.INSTANCE.mapContribution(contribution));
        dto.setObservations(null); // ToDo: pendiente ver en que entidad se guarda

        return dto;
    }

    private List<FlightExtendedInfoDto> mapFlightsWithServices(final Route route, final Contribution contribution) {
        return route.getFlights().stream().map(flight -> {
            FlightExtendedInfoDto dto = new FlightExtendedInfoDto();
            dto.setOperationType(route.getFile().getOperationType());
            dto.setPaxTotalNumber(flight.getSeatsC() + flight.getSeatsF() + flight.getSeatsY());
            dto.setBedsNumber(flight.getBeds());
            dto.setStretchersNumber(flight.getStretchers());
            dto.setOriginAirport(flight.getOrigin());
            dto.setDestinationAirport(flight.getDestination());
//            dto.setStartDate(flight.getDepartureTime()); // ToDo: pendiente cambio modelo
//            dto.setEndDate(flight.getDepartureTime()); // ToDo: calcular tiempo de vuelo y sumarlo
            dto.setPlateNumber(contribution.getAircraft().getPlateNumber());
            dto.setOperator(contribution.getAircraft().getOperator().getName());
            dto.setClient(route.getFile().getClient().getCode() + " | " + route.getFile().getClient().getName());
            dto.setCharge(null); // ToDo: de donde lo sacamos?
            dto.setFlightNumber(null);// ToDo: de donde lo sacamos?

            dto.setServices(mapFlightServices(flight.getId()));


/*
            final List<DistanceSpeedUtils> dsDataList = new ArrayList<>();

            DistanceSpeedUtils dsData;
            Optional<DistanceSpeedUtils> optionalData = dsDataList.stream().filter(dsu -> dsu.getOriginId().equals(origin.getId())
                    && dsu.getDestinationId().equals(destination.getId())
                    && dsu.getAircraftTypeId().equals(aircraft.getAircraftType().getId()))
                    .findFirst();
            if (optionalData.isPresent()) {
                dsData = optionalData.get();
            } else {
                dsData = calculateDistanceAndSpeed(aircraft.getAircraftType(), origin, destination);
                dsDataList.add(dsData);
            }
*/




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
