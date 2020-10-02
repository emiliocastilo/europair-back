package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.conversions.ConversionDataDTO;
import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
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
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.fleet.entity.AircraftTypeAverageSpeed;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import com.europair.management.rest.model.regions.entity.Region;
import com.europair.management.rest.model.regionscountries.repository.IRegionRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.entity.RouteAirport;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    private final Unit DEFAULT_SPEED_UNIT = Unit.KNOTS;
    private final Unit DEFAULT_DISTANCE_UNIT = Unit.NAUTIC_MILE;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private IRegionRepository regionRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<AircraftSearchResultDataDto> searchAircraft(final AircraftFilterDto filterDto) {

        Route route = routeRepository.findById(filterDto.getRouteId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + filterDto.getRouteId()));
        Airport origin = route.getAirports().stream()
                .min(Comparator.comparing(RouteAirport::getOrder))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No origin airport found for route with id: " + route.getId()))
                .getAirport();
        Airport destination = route.getAirports().stream()
                .filter(routeAirport -> !origin.getId().equals(routeAirport.getAirport().getId()))
                .min(Comparator.comparing(RouteAirport::getOrder))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No destination airport found for route with id: " + route.getId()))
                .getAirport();

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
            airportBaseIds.addAll(findNearbyAirports(filterDto, route));
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
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subcategory not found with id; " + filterDto.getSubcategoryId()));
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
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found with id: " + filterDto.getRegionId()));
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
                        dsDataList, aircraftTypeConnectionsMap, route))
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
                    dataDto.setMainBaseName(mainBase == null ? null : mainBase.getName());

                    dataDto.setConnectionFlights(
                            aircraftTypeConnectionsMap.getOrDefault(aircraft.getAircraftType().getId(), null));

                    return dataDto;
                }).collect(Collectors.toList());

        return result;
    }

    private List<Long> findNearbyAirports(final AircraftFilterDto filterDto, final Route route) {
        Airport destination = route.getAirports().stream()
                .max(Comparator.comparing(RouteAirport::getOrder))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No destination airport found for route with id: " + route.getId()))
                .getAirport();
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

        if (DEFAULT_DISTANCE_UNIT.equals(filterDto.getDistanceUnit())) {
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
            conversionData.setDstUnit(DEFAULT_DISTANCE_UNIT);
            conversionData.setDataToConvert(Arrays.asList(ctFrom, ctTo));

            List<Double> result = conversionService.convertData(conversionData);
            fromDistance = result.get(0);
            toDistance = result.get(1);
        }

        return remainingAirports.stream().filter(airport -> searchedAirports.stream()
                .anyMatch(origAirport -> {
                    double distance = Utils.getDistanceInKM(origAirport.getLatitude(), origAirport.getLongitude(),
                            airport.getLatitude(), airport.getLongitude());
                    return distance >= fromDistance && distance <= toDistance;
                }))
                .map(Airport::getId)
                .collect(Collectors.toList());
    }

    private boolean filterAircraftByRangeAndCalculateTime(
            final AircraftFilterDto filterDto, final Aircraft aircraft, final Airport origin, final Airport destination,
            @NotNull List<DistanceSpeedUtils> dsDataList, @NotNull Map<Long, Integer> aircraftTypeConnectionsMap,
            final Route route) {
        List<RouteAirport> routeAirports = route.getAirports().stream()
                .sorted(Comparator.comparing(RouteAirport::getOrder))
                .collect(Collectors.toList());

        // If distance has been calculated before we get the data, otherwise we do the calculation and save the data
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

        int maxConnections = filterDto.getMaxConnectingFlights() != null ? filterDto.getMaxConnectingFlights() : 0;
        // If connections data has been calculated before we get it, otherwise we do the calculation and save it
        Integer connections;
        if (aircraftTypeConnectionsMap.containsKey(aircraft.getAircraftType().getId())) {
            connections = aircraftTypeConnectionsMap.get(aircraft.getAircraftType().getId());
        } else {
            connections = null;
            for (int i = 0; i < routeAirports.size() - 1; i++) {
                try {
                    int flightConnections = Utils.calculateConnectingFlights(routeAirports.get(i).getAirport(),
                            routeAirports.get(i + 1).getAirport(), aircraft.getAircraftType(), conversionService);
                    if (connections == null || connections < flightConnections) {
                        connections = flightConnections;
                    }
                } catch (ResponseStatusException ignored) {
                } // If some data is missing for the calculation we still count the aircraft
            }
            aircraftTypeConnectionsMap.put(aircraft.getAircraftType().getId(), connections);
        }

        return connections == null || connections >= maxConnections;
    }


    private DistanceSpeedUtils calculateDistanceAndSpeed(final AircraftType aircraftType, final Airport origin, final Airport destination) {
        DistanceSpeedUtils dsData = new DistanceSpeedUtils();
        dsData.setOriginId(origin.getId());
        dsData.setDestinationId(destination.getId());
        dsData.setAircraftTypeId(aircraftType.getId());

        // Calculate distance
        double distance = Utils.getDistanceInNM(origin.getLatitude(), origin.getLongitude(), destination.getLatitude(),
                destination.getLongitude());
        dsData.setDistance(distance);

        // Filter avg speeds to get the one that matches the distance
        AircraftTypeAverageSpeed speed = aircraftType.getAverageSpeed().stream()
                .filter(avgSpeed -> {
                    List<Double> distanceRange = Arrays.asList(avgSpeed.getFromDistance(), avgSpeed.getToDistance());
                    if (!DEFAULT_DISTANCE_UNIT.equals(avgSpeed.getDistanceUnit())) {
                        // Convert distances to default unit
                        ConversionDataDTO.ConversionTuple ctFrom = new ConversionDataDTO.ConversionTuple();
                        ctFrom.setSrcUnit(avgSpeed.getDistanceUnit());
                        ctFrom.setValue(avgSpeed.getFromDistance());

                        ConversionDataDTO.ConversionTuple ctTo = new ConversionDataDTO.ConversionTuple();
                        ctTo.setSrcUnit(avgSpeed.getDistanceUnit());
                        ctTo.setValue(avgSpeed.getToDistance());

                        ConversionDataDTO conversionData = new ConversionDataDTO();
                        conversionData.setDstUnit(DEFAULT_DISTANCE_UNIT);
                        conversionData.setDataToConvert(Arrays.asList(ctFrom, ctTo));

                        List<Double> result = conversionService.convertData(conversionData);
                        distanceRange = Arrays.asList(result.get(0), result.get(1));
                    }

                    return distance >= distanceRange.get(0) && distance <= distanceRange.get(1);
                }).findFirst().orElse(null);

        Double speedInDefaultUnit = null;
        if (speed != null) {
            speedInDefaultUnit = speed.getAverageSpeed();
            if (!DEFAULT_SPEED_UNIT.equals(speed.getAverageSpeedUnit())) {
                // Convert speed to default unit
                ConversionDataDTO.ConversionTuple ct = new ConversionDataDTO.ConversionTuple();
                ct.setSrcUnit(speed.getAverageSpeedUnit());
                ct.setValue(speed.getAverageSpeed());

                ConversionDataDTO conversionData = new ConversionDataDTO();
                conversionData.setDstUnit(DEFAULT_SPEED_UNIT);
                conversionData.setDataToConvert(Collections.singletonList(ct));

                List<Double> result = conversionService.convertData(conversionData);
                speedInDefaultUnit = result.get(0);
            }
        }

        dsData.setSpeed(speedInDefaultUnit);

        return dsData;
    }


}
