package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.conversions.ConversionDataDTO;
import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.fleet.IAircraftSearchMapper;
import com.europair.management.impl.service.conversions.ConversionService;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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


        // ToDo checek near airports and add ids to filter
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


        List<AircraftSearchResultDataDto> result = aircraftFiltered.stream()
                .map(IAircraftSearchMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

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
                remainingAirports.add(airport);
            }
        });

        final Unit defaultDistanceUnit = Unit.KILOMETER;
        final Double fromDistance;
        final Double toDistance;

        if (defaultDistanceUnit.equals(filterDto.getDistanceUnit())) {
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
            conversionData.setDstUnit(defaultDistanceUnit);
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
}
