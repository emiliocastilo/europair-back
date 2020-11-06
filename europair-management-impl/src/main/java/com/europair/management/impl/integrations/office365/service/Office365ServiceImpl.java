package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.api.integrations.office365.dto.*;
import com.europair.management.api.integrations.office365.enums.Office365PlanningFlightActionType;
import com.europair.management.impl.integrations.office365.mappers.IOffice365Mapper;
import com.europair.management.impl.service.conversions.ConversionService;
import com.europair.management.impl.util.DistanceSpeedUtils;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.entity.FileAdditionalData;
import com.europair.management.rest.model.files.repository.FileAdditionalDataRepository;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.entity.FlightService;
import com.europair.management.rest.model.flights.repository.FlightRepository;
import com.europair.management.rest.model.flights.repository.FlightServiceRepository;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
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

    @Autowired
    private ConversionService conversionService;


    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private FileAdditionalDataRepository additionalDataRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private Office365Client office365Client;

    @Value("${europair.web.file.url}")
    private String fileUrl;


    @Override
    public ConfirmedOperationDto getConfirmedOperationData(Long routeId, Long contributionId) {
        Route route = routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));

        Contribution contribution = contributionRepository.findById(contributionId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + contributionId));

        return mapConfirmedOperation(route, contribution);
    }

    @Override
    public ResponseContributionFlights getEnabledFlightContributionInformation(Long contributionId) {

        ResponseContributionFlights responseContributionFlights = new ResponseContributionFlights();

        // preconditions
        Contribution contribution = this.contributionRepository.findById(contributionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + contributionId));

        Aircraft aircraft = this.aircraftRepository.findById(contribution.getAircraftId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found with id: " + contribution.getAircraftId()));

        Route route = this.routeRepository.findById(contribution.getRouteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + contribution.getRouteId()));


        List<DistanceSpeedUtils> dsDataList = new ArrayList<>();

        // first step: planningFlightsDTO -> fileSharingInfoDTO, flightSharingInfoDTO
        List<PlanningFlightsDTO> planningFlightsDTOS = this.getPlanningFlightsInfo(route.getId(), contributionId);


        // second step: aircraftSharingDTO
        AircraftSharingDTO aircraftSharingDTO = new AircraftSharingDTO();

        for (AircraftBase aircraftBase : aircraft.getBases()) {
            if (null != aircraftBase.getMainBase()) {
                aircraftSharingDTO.setBaseCode(aircraftBase.getAirport().getIataCode());
                aircraftSharingDTO.setBaseName(aircraftBase.getAirport().getName());
            }
        }
        if (null != aircraft.getAircraftType()) {
            aircraftSharingDTO.setTypeCode(aircraft.getAircraftType().getCode());
            aircraftSharingDTO.setTypeName(aircraft.getAircraftType().getDescription());

            if (null != aircraft.getAircraftType().getCategory()) {
                aircraftSharingDTO.setCategoryCode(aircraft.getAircraftType().getCategory().getCode());
                aircraftSharingDTO.setCategoryName(aircraft.getAircraftType().getCategory().getName());
            }

            if (null != aircraft.getAircraftType().getSubcategory()) {
                aircraftSharingDTO.setSubcategoryCode(aircraft.getAircraftType().getSubcategory().getCode());
                aircraftSharingDTO.setSubcategoryName(aircraft.getAircraftType().getSubcategory().getName());
            }
        }

        // third step: operatorSharingDTO
        OperatorSharingDTO operatorSharingDTO = new OperatorSharingDTO();

        if (null != contribution.getOperator()) {
            operatorSharingDTO.setIataCode(contribution.getOperator().getIataCode());
            operatorSharingDTO.setIcaoCode(contribution.getOperator().getIcaoCode());
            operatorSharingDTO.setName(contribution.getOperator().getName());
        }

        // union of data
        responseContributionFlights.setAircraftSharingDTO(aircraftSharingDTO);
        responseContributionFlights.setOperatorSharingDTO(operatorSharingDTO);
        responseContributionFlights.setPlanningFlightsDTOS(planningFlightsDTOS);

        return responseContributionFlights;
    }

    @Override
    public List<PlanningFlightsDTO> getPlanningFlightsInfo(Long routeId, Long contributionId) {

        Route route = routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));

        Contribution contribution = contributionRepository.findById(contributionId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + contributionId));

        return mapPlanningFlightsInfo(route, contribution);

    }

    @Override
    public List<MinimalRouteInfoToSendThePlanningFlightsDTO> getAllRoutesToSendPlanningFlights(Long fileId) {

        Optional<File> optFile = this.fileRepository.findById(fileId);
        List<MinimalRouteInfoToSendThePlanningFlightsDTO> flightListToSend = new ArrayList<>();

        if (optFile.isPresent()) {
            flightListToSend =
                    optFile.get()
                            .getRoutes().stream()
                            .map(Route::getContributions)
                            .flatMap(Collection::stream)
                            .map(contribution -> {
                                    if(contribution.getContributionState().equals(ContributionStatesEnum.WON)) {
                                        return contribution.getRoute().getRotations()
                                                .stream()
                                                .filter(route -> route.getRouteState().equals(RouteStatesEnum.WON))
                                                .map(route -> {

                                                    MinimalRouteInfoToSendThePlanningFlightsDTO info = new MinimalRouteInfoToSendThePlanningFlightsDTO();
                                                    info.setRouteId(route.getId());
                                                    info.setContributionId(contribution.getId());

                                                    return info;
                                                }).collect(Collectors.toList());
                                    } else {
                                        return null;
                                    }
                            }).flatMap(Collection::stream).collect(Collectors.toList());
        }
        return flightListToSend;
    }


    public List<MinimalRouteInfoToSendThePlanningFlightsDTO> getAllRoutesToSendPlanningFlights2(Long fileId) {

        Optional<File> optFile = this.fileRepository.findById(fileId);
        List<MinimalRouteInfoToSendThePlanningFlightsDTO> flightListToSend = new ArrayList<>();

        if (optFile.isPresent()) {
            flightListToSend =
                    optFile.get()
                            .getRoutes().stream()
                            .map(Route::getRotations)
                            .flatMap(Collection::stream)
                            .filter(route -> route.getRouteState().equals(RouteStatesEnum.WON))
                            .map(route -> {
                                MinimalRouteInfoToSendThePlanningFlightsDTO info = new MinimalRouteInfoToSendThePlanningFlightsDTO();
                                info.setRouteId(route.getId());

                                List<Long> listOfContributionIdInStateWON = route.getParentRoute()
                                        .getContributions()
                                        .stream()
                                        .filter(contribution -> contribution.getContributionState().equals(ContributionStatesEnum.WON))
                                        .map(contribution -> contribution.getId())
                                        .collect(Collectors.toList());

                                info.setContributionId(listOfContributionIdInStateWON.isEmpty() ? listOfContributionIdInStateWON.get(0) : null);

                                return info;
                            }).collect(Collectors.toList());
        }
        return flightListToSend;
    }

    ///////////////////////
    // Mapping functions //
    ///////////////////////

    private ConfirmedOperationDto mapConfirmedOperation(final Route route, final Contribution contribution) {
        final List<DistanceSpeedUtils> dsDataList = new ArrayList<>();

        ConfirmedOperationDto dto = new ConfirmedOperationDto();

        FileSharingExtendedInfoDto fileInfoDto;
        FileAdditionalData additionalData = additionalDataRepository.findByFileId(route.getFileId()).stream()
                .findAny().orElse(null);
        if (additionalData == null) {
            fileInfoDto = IOffice365Mapper.INSTANCE.mapFile(route.getFile());
        } else {
            fileInfoDto = IOffice365Mapper.INSTANCE.mapFile(additionalData);
        }
        fileInfoDto.setFileUrl(fileUrl + route.getFile().getId());
        dto.setFileInfo(fileInfoDto);

        dto.setFlightsInfo(mapFlightsWithServices(route, contribution, dsDataList));

        ContributionDataDto contributionData = IOffice365Mapper.INSTANCE.mapContribution(contribution);
        if (!CollectionUtils.isEmpty(contribution.getLineContributionRoute())) {
            contributionData.setLines(contribution.getLineContributionRoute().stream()
                    .map(IOffice365Mapper.INSTANCE::mapContributionLine)
                    .collect(Collectors.toList()));
        }
        dto.setContributionInfo(contributionData);

        return dto;
    }

    private List<FlightExtendedInfoDto> mapFlightsWithServices(final Route route, final Contribution contribution,
                                                               final List<DistanceSpeedUtils> dsDataList) {
        List<Flight> routeFlights;
        if (route.getParentRoute() == null) {
            // Route
            routeFlights = route.getRotations().stream()
                    .map(Route::getFlights)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } else {
            // Rotation
            routeFlights = route.getFlights();
        }

        return routeFlights.stream().map(flight -> {
            FlightExtendedInfoDto dto = new FlightExtendedInfoDto(
                    getFlightSharingInfoDTO(route, contribution, dsDataList, flight));
            dto.setServices(mapFlightServices(flight.getId()));
            return dto;
        }).collect(Collectors.toList());
    }


    private List<PlanningFlightsDTO> mapPlanningFlightsInfo(Route route, Contribution contribution) {

        final List<DistanceSpeedUtils> dsDataList = new ArrayList<>();

        List<Flight> routeFlights;

        // retrieves flights list from route or rotation
        if (route.getParentRoute() == null) {
            // Route
            routeFlights = route.getRotations().stream()
                    .map(Route::getFlights)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } else {
            // Rotation
            routeFlights = route.getFlights();
        }

        // map info values
        return routeFlights.stream()
                .map(flight -> mapPlanningFlight(route, contribution, dsDataList, flight))
                .collect(Collectors.toList());
    }

    private PlanningFlightsDTO mapPlanningFlight(Route route, Contribution contribution, List<DistanceSpeedUtils> dsDataList, Flight flight) {
        PlanningFlightsDTO dto = new PlanningFlightsDTO();

        FileSharingInfoDTO fileSharingInfo = IOffice365Mapper.INSTANCE.mapFile(route);
        fileSharingInfo.setFileUrl(fileUrl + route.getFile().getId());
        dto.setFileSharingInfoDTO(fileSharingInfo);
        dto.setFlightSharingInfoDTO(getFlightSharingInfoDTO(route, contribution, dsDataList, flight));
        dto.setActionType(Office365PlanningFlightActionType.CREATE);
        if (Boolean.TRUE.equals(flight.getSentPlanning())){
            dto.setActionType(Office365PlanningFlightActionType.UPDATE);
        }

        return dto;
    }


    private FlightSharingInfoDTO getFlightSharingInfoDTO(Route route, Contribution contribution,
                                                         List<DistanceSpeedUtils> dsDataList, Flight flight) {
        Airport origin = flight.getOrigin();
        Airport destination = flight.getDestination();
        Operator operator = contribution.getAircraft().getOperator();

        FlightSharingInfoDTO dto = new FlightSharingInfoDTO();
        dto.setFlightId(flight.getId());
        dto.setOperationType(route.getFile().getOperationType());
        dto.setPaxTotalNumber((flight.getSeatsC() == null ? 0 : flight.getSeatsC()) +
                (flight.getSeatsF() == null ? 0 : flight.getSeatsF()) +
                (flight.getSeatsY() == null ? 0 : flight.getSeatsY()));
        dto.setBedsNumber(flight.getBeds());
        dto.setStretchersNumber(flight.getStretchers());
        dto.setOriginAirport(origin.getIataCode() + " | " + origin.getIcaoCode() + " | " + origin.getName());
        dto.setDestinationAirport(destination.getIataCode() + " | " + destination.getIcaoCode() + " | " + destination.getName());
        dto.setPlateNumber(contribution.getAircraft().getPlateNumber());
        dto.setOperator(operator.getIataCode() + " | " + operator.getIcaoCode() + " | " + operator.getName());
        dto.setClient(route.getFile().getClient().getCode() + " | " + route.getFile().getClient().getName());
        dto.setCharge(contribution.getCargoAirborne()); // ToDo: de donde lo sacamos?
        dto.setFlightNumber(flight.getFlightNumber());

        // Dates
        dto.setStartDate(flight.getDepartureTime());
        dto.setLocalStartDate(Utils.TimeConverter.getLocalTimeInOtherUTC(flight.getTimeZone(), flight.getDepartureTime(),
                origin.getTimeZone()));

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
        if (null != dto.getEndDate()) {
            dto.setLocalEndDate(Utils.TimeConverter.getLocalTimeInOtherUTC(flight.getTimeZone(), dto.getEndDate(), destination.getTimeZone()));
        }

        return dto;
    }


    private List<FlightServiceDataDto> mapFlightServices(final Long flightId) {
        List<FlightService> flightServices = flightServiceRepository.findAllByFlightId(flightId);
        return CollectionUtils.isEmpty(flightServices) ? null : flightServices.stream()
                .map(IOffice365Mapper.INSTANCE::mapFlightService)
                .collect(Collectors.toList());
    }

}
