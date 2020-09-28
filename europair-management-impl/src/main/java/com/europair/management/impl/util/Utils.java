package com.europair.management.impl.util;

import com.europair.management.api.dto.conversions.ConversionDataDTO;
import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.api.enums.UTCEnum;
import com.europair.management.impl.service.conversions.ConversionService;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.common.Restriction;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.entity.RouteAirport;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static final String FILTER_PARAM_PREFIX = "filter_";
    public static final String FILTER_PARAM_VALUE_SEPARATOR = ",";

    public static CoreCriteria mapFilterRequestParams(Map<String, String> reqParams) {

        try {
            CoreCriteria criteria = new CoreCriteria();
            criteria.setRestrictions(new ArrayList<>());

            if (reqParams != null && !reqParams.isEmpty()) {
                criteria.setRestrictions(
                        reqParams.entrySet().stream()
                                .filter(entry -> entry.getKey().startsWith(FILTER_PARAM_PREFIX))
                                .map(entry -> {
                                    String[] paramValues = entry.getValue().split(FILTER_PARAM_VALUE_SEPARATOR);
                                    OperatorEnum operator;
                                    try {
                                        operator = OperatorEnum.valueOf(paramValues[1].toUpperCase());
                                    } catch (IllegalArgumentException e) {
                                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                "Invalid filter params, operator not valid: " + paramValues[1], e);
                                    }

                                    return Restriction.builder()
                                            .column(entry.getKey().replace(FILTER_PARAM_PREFIX, ""))
                                            .value(paramValues[0])
                                            .operator(operator)
                                            .queryOperator(paramValues.length > 2 ? Predicate.BooleanOperator.OR : Predicate.BooleanOperator.AND)
                                            .build();
                                }).collect(Collectors.toList()));
            }

            return criteria;

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filter params. ", e);
        }
    }

    public static void addCriteriaIfNotExists(CoreCriteria criteria,
                                              @NotNull final String filterName,
                                              @NotNull final OperatorEnum operator, final String filterValue) {

        // Add filter if not present
        if (criteria.getRestrictions().stream()
                .noneMatch(restriction -> restriction.getColumn().equals(filterName))) {
            criteria.getRestrictions().add(Restriction.builder()
                    .column(filterName)
                    .value(filterValue)
                    .operator(operator)
                    .build()
            );
        }
    }

    /**
     * Maps string of airport codes of route as List
     * @param routeLabel String of route label with format "XXX-YYY-ZZZ"
     * @return List of Iata Codes
     */
    public static List<String> mapRouteAirportIataCodes(final String routeLabel) {
         String trimmedLabel = routeLabel.replaceAll("\\s+", "");
         return Arrays.asList(trimmedLabel.split("-"));
    }


    /**
     * Utils.TimeConverter API to convert time from:
     * * UTC: getLocalTimeInOtherUTC
     *
     * * (if we have more conversiones must add the description here)
     */
    public static class TimeConverter {

        /**
         * Operation to transform date time from an utc indicator to another
         * @param fromUTCIndicator
         * @param time
         * @param toUTCIndicator
         * @return
         */
        public static LocalTime getLocalTimeInOtherUTC(UTCEnum fromUTCIndicator, String time, UTCEnum toUTCIndicator) {
            LocalTime utcZeroTime;
            // If fromUTCIndicator is < 0 to transform into UTC +0 we have to substract the minutes
            if (0 < fromUTCIndicator.getHours()) {
                utcZeroTime = addHoursMinutesToLocalTime(LocalTime.parse(time), (-fromUTCIndicator.getHours()), fromUTCIndicator.getMinutes());
            } else {
                utcZeroTime = addHoursMinutesToLocalTime(LocalTime.parse(time), (-fromUTCIndicator.getHours()), (-fromUTCIndicator.getMinutes()));
            }

            LocalTime utcTime = null;
            //After that we have to add the hours from toUTCIndicator
            // If toUTCIndicator is < 0 to transform into UTC +0 we have to substract the minutes
            if (0 < toUTCIndicator.getHours()) {
                utcTime = addHoursMinutesToLocalTime(utcZeroTime, toUTCIndicator.getHours(), toUTCIndicator.getMinutes());
            } else {
                utcTime = addHoursMinutesToLocalTime(utcZeroTime, toUTCIndicator.getHours(), (-toUTCIndicator.getMinutes()));
            }
            return utcTime;
        }

        private static LocalTime addHoursMinutesToLocalTime(LocalTime localTime, Integer hours, Integer minutes) {

            LocalTime res = null;

            res = localTime.plusHours(hours);
            res = res.plusMinutes(minutes);

            return res;

        }

    }


    private static double calculateDistance(final double origLat, final double origLon, final double destLat, final double destLon) {
        return Math.acos(
                Math.cos(Math.toRadians(90 - origLat)) * Math.cos(Math.toRadians(90 - destLat))
                        + Math.sin(Math.toRadians(90 - origLat))
                        * Math.sin(Math.toRadians(90 - destLat))
                        * Math.cos(Math.toRadians(origLon - destLon))
        );
    }

    public static double getDistanceInKM(final double origLat, final double origLon, final double destLat, final double destLon) {
        double earthRadiusInKM = 6371;// Approximate radius of the earth in kilometers
        return calculateDistance(origLat, origLon, destLat, destLon) * earthRadiusInKM;
    }

    public static double getDistanceInNM(final double origLat, final double origLon, final double destLat, final double destLon) {
        double earthRadiusInNM = 3440.065;// Approximate radius of the earth in nautical miles
        return calculateDistance(origLat, origLon, destLat, destLon) * earthRadiusInNM;
    }

    /**
     * Maps the route distance between airports as a percentage relative to the total route distance
     *
     * @param route Route entity with route airports
     * @return Map with key as a pair of origin airport Id, and destination airport id and the distance between them in KM.
     */
    public static Map<Pair<Long, Long>, Double> getRouteAirportDistancePercentage(@NotNull final Route route) {
        Map<Pair<Long, Long>, Double> resultMap = new HashMap<>();
        List<RouteAirport> airportList = route.getAirports().stream()
                .sorted(Comparator.comparing(RouteAirport::getOrder))
                .collect(Collectors.toList());
        double totalRouteDistance = 0D;
        List<Double> distances = new ArrayList<>();

        // Calculate distances and total route distance
        for (int i = 0; i < airportList.size() - 1; i++) {
            Airport origin = airportList.get(i).getAirport();
            Airport destination = airportList.get(i + 1).getAirport();

            double distance = Utils.getDistanceInKM(origin.getLatitude(), origin.getLongitude(), destination.getLatitude(),
                    destination.getLongitude());
            distances.add(distance);
            totalRouteDistance = totalRouteDistance + distance;
        }

        // Calculate distance percentage with total and fill map data
        for (int i = 0; i < airportList.size() - 1; i++) {
            Airport origin = airportList.get(i).getAirport();
            Airport destination = airportList.get(i + 1).getAirport();

            Pair<Long, Long> key = Pair.of(origin.getId(), destination.getId());
            Double distance = distances.get(i);
            double finalTotalRouteDistance = totalRouteDistance;
            resultMap.computeIfAbsent(key, s -> distance * 100 / finalTotalRouteDistance);
        }

        return resultMap;
    }

    public static int calculateConnectingFlights(final Airport origin, final Airport destination, final AircraftType aircraftType,
                                                 final ConversionService conversionService) {
        if (origin.getLatitude() == null || origin.getLongitude() == null || destination.getLatitude() == null || destination.getLongitude() == null) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "One of the airports doesn't have all the coordinates data to calculate the distance.");
        }
        if (aircraftType.getFlightRange() == null || aircraftType.getFlightRangeUnit() == null) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "No flight range data for aircraft type with id: " + aircraftType.getId());
        }
        Unit defaultUnit = Unit.NAUTIC_MILE;
        Double flightRangeInDefaultUnit = aircraftType.getFlightRange();
        if (!Unit.NAUTIC_MILE.equals(aircraftType.getFlightRangeUnit())) {
            ConversionDataDTO.ConversionTuple ct = new ConversionDataDTO.ConversionTuple();
            ct.setSrcUnit(aircraftType.getFlightRangeUnit());
            ct.setValue(aircraftType.getFlightRange());
            ConversionDataDTO conversionData = new ConversionDataDTO();
            conversionData.setDstUnit(defaultUnit);
            conversionData.setDataToConvert(Collections.singletonList(ct));
            List<Double> result = conversionService.convertData(conversionData);
            if (CollectionUtils.isEmpty(result)) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Conversion service error while converting flight ranges.");
            }
            flightRangeInDefaultUnit = result.get(0);
        }
        double distance = Utils.getDistanceInNM(origin.getLatitude(), origin.getLongitude(), destination.getLatitude(),
                destination.getLongitude());

        return (int) Math.ceil(distance / flightRangeInDefaultUnit) - 1;
    }


    /**
     * Utils inner class containing constant values of the application
     */
    public static class Constants {
        public static final String SPAIN_CODE = "ES";
        public static final String TAX_ES_CODE = "ES";
        public static final String TAX_ES_REDUCED_CODE = "ES_REDUCED";
        public static final String TAX_ES_IGIC_CODE = "ES_IGIC";
    }

}
