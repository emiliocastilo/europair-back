package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.conversions.ConversionDataDTO;
import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.fleet.IAircraftSearchMapper;
import com.europair.management.impl.service.conversions.ConversionService;
import com.europair.management.impl.util.DistanceSpeedUtils;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.fleet.entity.AircraftTypeAverageSpeed;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AircraftSearchServiceImpl implements IAircraftSearchService {

    @Value("${europair.aircraft.search.default.category.operation.acmi}")
    private String DEFAULT_TYPE_FOR_OPERATION_ACMI;

    @Value("${europair.aircraft.search.default.category.operation.commercial}")
    private String DEFAULT_TYPE_FOR_OPERATION_COMMERCIAL;

    @Value("${europair.aircraft.search.default.category.operation.executive}")
    private String DEFAULT_TYPE_FOR_OPERATION_EXECUTIVE;

    @Value("${europair.aircraft.search.default.category.operation.charge}")
    private String DEFAULT_TYPE_FOR_OPERATION_CHARGE;

    @Value("${europair.aircraft.search.default.category.operation.group}")
    private String DEFAULT_TYPE_FOR_OPERATION_GROUP;

    private final Unit DEFAULT_SPEED_UNIT = Unit.KNOTS;
    private final Unit DEFAULT_DISTANCE_UNIT = Unit.NAUTIC_MILE;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<AircraftSearchResultDataDto> searchAircraft(final AircraftFilterDto filterDto) {

        List<Long> airportBaseIds = new ArrayList<>(filterDto.getBaseIds());
        if (!CollectionUtils.isEmpty(filterDto.getBaseIds()) && filterDto.getFromDistance() != null
                && filterDto.getToDistance() != null && filterDto.getDistanceUnit() != null) {
            airportBaseIds.addAll(findNearbyAirports(filterDto));
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
                    .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id; " + filterDto.getSubcategoryId()));
            if (!Boolean.TRUE.equals(filterDto.getExactSubcategory())) {
                minSubcategory = subCategory.getOrder();
                filterDto.setCategoryId(subCategory.getParentCategory().getId());
            }
        }

        List<Aircraft> aircraftFiltered = aircraftRepository.searchAircraft(
                airportBaseIds,
                filterDto.getCountryIds(),
                filterDto.getSeats(),
                filterDto.getBeds(),
                filterDto.getCategoryId(),
                filterDto.getSubcategoryId(),
                filterDto.getExactSubcategory(),
                minSubcategory,
                filterDto.getAmbulance(),
                filterDto.getAircraftTypes(),
                filterDto.getOperators()
        );

        final Airport destinationAirport = airportRepository.findById(filterDto.getDestinationAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Destinarion Airport not found with id: " + filterDto.getDestinationAirportId()));

        final List<DistanceSpeedUtils> dsDataList = new ArrayList<>();

        List<AircraftSearchResultDataDto> result = aircraftFiltered.stream()
                .filter(aircraft -> filterAircraftByRangeAndCalculateTime(aircraft, destinationAirport, dsDataList))
                .map(aircraft -> {
                    // ToDo: que origen usamos??
                    final Long originId = aircraft.getBases().stream()
                            .filter(AircraftBase::getMainBase)
                            .findFirst()
                            .map(aircraftBase -> aircraftBase.getAirport().getId())
                            .orElse(null);

                    AircraftSearchResultDataDto dataDto = IAircraftSearchMapper.INSTANCE.toDto(aircraft);
                    dataDto.setTimeInHours(dsDataList.stream()
                            .filter(dsu -> dsu.getAircraftTypeId().equals(aircraft.getAircraftType().getId())
                                    && dsu.getOriginId().equals(originId) && dsu.getDestinationId().equals(destinationAirport.getId()))
                            .findFirst().map(DistanceSpeedUtils::getTimeInHours).orElse(null));

                    return dataDto;
                }).collect(Collectors.toList());

        return result;
    }

    private List<Long> findNearbyAirports(final AircraftFilterDto filterDto) {
        // Find all active airports that have latitude and longitude values
        List<Airport> allAirports = airportRepository.findByRemovedAtNullAndLatitudeNotNullAndLongitudeNotNull();

        List<Airport> searchedAirports = new ArrayList<>();
        List<Airport> remainingAirports = new ArrayList<>();
        allAirports.forEach(airport -> {
            if (filterDto.getBaseIds().contains(airport.getId())) {
                searchedAirports.add(airport);
            } else {
                if (!airport.getId().equals(filterDto.getDestinationAirportId())) {
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

    private boolean filterAircraftByRangeAndCalculateTime(final Aircraft aircraft, final Airport destinationAirport,
                                                          @NotNull List<DistanceSpeedUtils> dsDataList) {

        // ToDo: cual es el origen???
        Airport origin = aircraft.getBases().stream().filter(AircraftBase::getMainBase).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No main base found for Aircraft with id: " + aircraft.getId()))
                .getAirport();

        // If distance has been calculated before we get the data, otherwise we do the calculation and save the data
        DistanceSpeedUtils dsData;
        Optional<DistanceSpeedUtils> optionalData = dsDataList.stream().filter(dsu -> dsu.getOriginId().equals(origin.getId())
                && dsu.getDestinationId().equals(destinationAirport.getId())
                && dsu.getAircraftTypeId().equals(aircraft.getAircraftType().getId()))
                .findFirst();
        if (optionalData.isPresent()) {
            dsData = optionalData.get();
        } else {
            dsData = calculateDistanceAndSpeed(aircraft.getAircraftType(), origin, destinationAirport);
            dsDataList.add(dsData);
        }

        Double flightRangeInDefaultUnit = aircraft.getAircraftType().getFlightRange();
        if (aircraft.getAircraftType().getFlightRange() != null && !DEFAULT_DISTANCE_UNIT.equals(aircraft.getAircraftType().getFlightRangeUnit())) {
            // Convert flight range to default unit
            ConversionDataDTO.ConversionTuple ct = new ConversionDataDTO.ConversionTuple();
            ct.setSrcUnit(aircraft.getAircraftType().getFlightRangeUnit());
            ct.setValue(aircraft.getAircraftType().getFlightRange());

            ConversionDataDTO conversionData = new ConversionDataDTO();
            conversionData.setDstUnit(DEFAULT_DISTANCE_UNIT);
            conversionData.setDataToConvert(Collections.singletonList(ct));

            List<Double> result = conversionService.convertData(conversionData);
            flightRangeInDefaultUnit = result.get(0);
        }

        return flightRangeInDefaultUnit == null || flightRangeInDefaultUnit >= dsData.getDistance();
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
