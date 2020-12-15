package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.conversions.ConversionDataDTO;
import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.fleet.IAircraftSearchMapper;
import com.europair.management.impl.service.conversions.ConversionService;
import com.europair.management.impl.util.DistanceSpeedUtils;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.regions.entity.Region;
import com.europair.management.rest.model.regions.repository.RegionRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AircraftSearchServiceImpl implements IAircraftSearchService {

    @Value("${europair.aircraft.search.default.category.operation.acmi}")
    private static String DEFAULT_TYPE_FOR_OPERATION_ACMI;

    @Value("${europair.aircraft.search.default.category.operation.commercial}")
    private static String DEFAULT_TYPE_FOR_OPERATION_COMMERCIAL;

    @Value("${europair.aircraft.search.default.category.operation.executive}")
    private static String DEFAULT_TYPE_FOR_OPERATION_EXECUTIVE;

    @Value("${europair.aircraft.search.default.category.operation.charge}")
    private static String DEFAULT_TYPE_FOR_OPERATION_CHARGE;

    @Value("${europair.aircraft.search.default.category.operation.group}")
    private static String DEFAULT_TYPE_FOR_OPERATION_GROUP;


    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<AircraftSearchResultDataDto> searchAircraft(final AircraftFilterDto filterDto) {

        Route route = routeRepository.findById(filterDto.getRouteId()).orElseThrow(() ->
                Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NOT_FOUND, String.valueOf(filterDto.getRouteId())));

        Route rotationSample = route.getParentRoute() == null ? route.getRotations().get(0) : route;

        Flight firstFlight = rotationSample.getFlights().stream().min(Comparator.comparing(Flight::getOrder))
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_FIRST_FLIGHT_NOT_FOUND,
                        String.valueOf(rotationSample.getId())));
        Airport origin = firstFlight.getOrigin();
        Airport destination = firstFlight.getDestination();

        Set<Long> airportBaseIds = null;
        if (!CollectionUtils.isEmpty(filterDto.getBaseIds())) {
            airportBaseIds = new HashSet<>(filterDto.getBaseIds());
        }
        Set<Long> countryIds = null;
        if (!CollectionUtils.isEmpty(filterDto.getCountryIds())) {
            countryIds = new HashSet<>(filterDto.getCountryIds());
        }

        // Nearby Airports filter setup
        if (!CollectionUtils.isEmpty(filterDto.getBaseIds()) && !CollectionUtils.isEmpty(airportBaseIds) &&
                filterDto.getFromDistance() != null && filterDto.getToDistance() != null && filterDto.getDistanceUnit() != null) {
            airportBaseIds.addAll(findNearbyAirports(filterDto, rotationSample));
        }

        if (filterDto.getOperationType() != null && filterDto.getCategoryId() == null) {
            String defaultTypeCode = switch (filterDto.getOperationType()) {
                case ACMI -> DEFAULT_TYPE_FOR_OPERATION_ACMI;
                case COMMERCIAL -> DEFAULT_TYPE_FOR_OPERATION_COMMERCIAL;
                case EXECUTIVE -> DEFAULT_TYPE_FOR_OPERATION_EXECUTIVE;
                case CHARGE -> DEFAULT_TYPE_FOR_OPERATION_CHARGE;
                case GROUP -> DEFAULT_TYPE_FOR_OPERATION_GROUP;
            };

            if (!Strings.isEmpty(defaultTypeCode)) {
                aircraftCategoryRepository.findByCode(defaultTypeCode)
                        .ifPresent(aircraftCategory -> filterDto.setCategoryId(aircraftCategory.getId()));
            }
        }

        Integer minSubcategory = null;
        if (filterDto.getSubcategoryId() != null) {
            AircraftCategory subCategory = aircraftCategoryRepository.findById(filterDto.getSubcategoryId())
                    .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_SUBCATEGORY_NOT_FOUND,
                            String.valueOf(filterDto.getSubcategoryId())));
            if (!Boolean.TRUE.equals(filterDto.getExactSubcategory())) {
                minSubcategory = subCategory.getOrder();
                filterDto.setCategoryId(subCategory.getParentCategory().getId());
            }
        }

        // Region filter setup
        Set<Long> regionAirportIds = null;
        Set<Long> regionCountryIds = null;
        if (filterDto.getRegionId() != null) {
            Region region = regionRepository.findById(filterDto.getRegionId()).orElseThrow(() ->
                    Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.REGION_NOT_FOUND, String.valueOf(filterDto.getRegionId())));
            regionAirportIds = region.getAirports().stream().map(Airport::getId).collect(Collectors.toSet());
            regionCountryIds = region.getCountries().stream().map(Country::getId).collect(Collectors.toSet());
        }

        List<Aircraft> aircraftFiltered = aircraftRepository.searchAircraft(
                airportBaseIds,
                countryIds,
                filterDto.getSeats(),
                filterDto.getSeatingF(),
                filterDto.getSeatingC(),
                filterDto.getSeatingY(),
                filterDto.getSeatingFC(),
                filterDto.getBeds(),
                filterDto.getCategoryId(),
                filterDto.getSubcategoryId(),
                filterDto.getExactSubcategory(),
                minSubcategory,
                filterDto.getAmbulance(),
                filterDto.getAircraftTypes(),
                filterDto.getOperators(),
                filterDto.getRegionId(), regionAirportIds, regionCountryIds
        );

        final List<DistanceSpeedUtils> dsDataList = new ArrayList<>();
        final Map<Long, Integer> aircraftTypeConnectionsMap = new HashMap<>();

        List<AircraftSearchResultDataDto> result = aircraftFiltered.stream()
                .filter(aircraft -> filterAircraftByRangeAndCalculateTime(filterDto, aircraft, origin, destination,
                        dsDataList, aircraftTypeConnectionsMap, rotationSample))
                .map(aircraft -> {
                    AircraftSearchResultDataDto dataDto = IAircraftSearchMapper.INSTANCE.toDto(aircraft);
                    dataDto.setTimeInHours(dsDataList.stream()
                            .filter(dsu -> dsu.getAircraftTypeId().equals(aircraft.getAircraftType().getId())
                                    && dsu.getOriginId().equals(origin.getId()) && dsu.getDestinationId().equals(destination.getId()))
                            .findFirst().map(DistanceSpeedUtils::getTimeInHours).orElse(null));

                    Airport mainBase = aircraft.getBases().stream()
                            .filter(AircraftBase::getMainBase)
                            .findAny()
                            .map(AircraftBase::getAirport)
                            .orElse(null);
                    dataDto.setMainBaseId(mainBase == null ? null : mainBase.getId());
                    dataDto.setMainBaseIataCode(mainBase == null ? null : mainBase.getIataCode());

                    dataDto.setConnectionFlights(
                            aircraftTypeConnectionsMap.getOrDefault(aircraft.getAircraftType().getId(), null));

                    return dataDto;
                }).collect(Collectors.toList());

        return result;
    }

    private List<Long> findNearbyAirports(final AircraftFilterDto filterDto, final Route route) {
        Flight lastFlight = route.getFlights().stream().max(Comparator.comparing(Flight::getOrder))
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_LAST_FLIGHT_NOT_FOUND,
                        String.valueOf(route.getId())));
        Airport destination = lastFlight.getDestination();
        // Find all active airports that have latitude and longitude values
        List<Airport> allAirports = airportRepository.findByRemovedAtNullAndLatitudeNotNullAndLongitudeNotNull();

        List<Airport> searchedAirports = new ArrayList<>();
        List<Airport> remainingAirports = new ArrayList<>();
        allAirports.forEach(airport -> {
            if (filterDto.getBaseIds().contains(airport.getId())) {
                searchedAirports.add(airport);
            } else {
                if (!airport.getId().equals(destination.getId())) {
                    remainingAirports.add(airport);
                }
            }
        });

        final Double fromDistance;
        final Double toDistance;

        if (Utils.Constants.DEFAULT_DISTANCE_UNIT.equals(filterDto.getDistanceUnit())) {
            fromDistance = filterDto.getFromDistance();
            toDistance = filterDto.getToDistance();

        } else {
            // Convert distances to default unit
            ConversionDataDTO.ConversionTuple ctFrom = new ConversionDataDTO.ConversionTuple();
            ctFrom.setSrcUnit(filterDto.getDistanceUnit());
            ctFrom.setValue(filterDto.getFromDistance());

            ConversionDataDTO.ConversionTuple ctTo = new ConversionDataDTO.ConversionTuple();
            ctTo.setSrcUnit(filterDto.getDistanceUnit());
            ctTo.setValue(filterDto.getToDistance());

            ConversionDataDTO conversionData = new ConversionDataDTO();
            conversionData.setDstUnit(Utils.Constants.DEFAULT_DISTANCE_UNIT);
            conversionData.setDataToConvert(Arrays.asList(ctFrom, ctTo));

            List<Double> result = conversionService.convertData(conversionData);
            fromDistance = result.get(0);
            toDistance = result.get(1);
        }

        return remainingAirports.stream().filter(airport -> searchedAirports.stream()
                .anyMatch(origAirport -> {
                    double distance = Unit.NAUTIC_MILE.equals(Utils.Constants.DEFAULT_DISTANCE_UNIT) ?
                            Utils.getDistanceInNM(origAirport.getLatitude(), origAirport.getLongitude(), airport.getLatitude(), airport.getLongitude()) :
                            Utils.getDistanceInKM(origAirport.getLatitude(), origAirport.getLongitude(), airport.getLatitude(), airport.getLongitude());
                    return distance >= fromDistance && distance <= toDistance;
                }))
                .map(Airport::getId)
                .collect(Collectors.toList());
    }

    private boolean filterAircraftByRangeAndCalculateTime(
            final AircraftFilterDto filterDto, final Aircraft aircraft, final Airport origin, final Airport destination,
            @NotNull List<DistanceSpeedUtils> dsDataList, @NotNull Map<Long, Integer> aircraftTypeConnectionsMap,
            final Route route) {

        // If distance has been calculated before we get the data, otherwise we do the calculation and save the data
        DistanceSpeedUtils dsData;
        Optional<DistanceSpeedUtils> optionalData = dsDataList.stream().filter(dsu -> dsu.getOriginId().equals(origin.getId())
                && dsu.getDestinationId().equals(destination.getId())
                && dsu.getAircraftTypeId().equals(aircraft.getAircraftType().getId()))
                .findFirst();
        if (optionalData.isPresent()) {
            dsData = optionalData.get();
        } else {
            dsData = Utils.calculateDistanceAndSpeed(conversionService, aircraft.getAircraftType(), origin, destination);
            dsDataList.add(dsData);
        }

        int maxConnections = filterDto.getMaxConnectingFlights() != null ? filterDto.getMaxConnectingFlights() : 0;
        // If connections data has been calculated before we get it, otherwise we do the calculation and save it
        Integer connections;
        if (aircraftTypeConnectionsMap.containsKey(aircraft.getAircraftType().getId())) {
            connections = aircraftTypeConnectionsMap.get(aircraft.getAircraftType().getId());
        } else {
            connections = null;
            for (Flight f : route.getFlights()) {
                try {
                    int flightConnections = Utils.calculateConnectingFlights(f.getOrigin(), f.getDestination(),
                            aircraft.getAircraftType(), conversionService);
                    if (connections == null || connections < flightConnections) {
                        connections = flightConnections;
                    }
                } catch (ResponseStatusException ignored) {
                } // If some data is missing for the calculation we still count the aircraft
            }
            aircraftTypeConnectionsMap.put(aircraft.getAircraftType().getId(), connections);
        }

        return connections == null || connections <= maxConnections;
    }

}
