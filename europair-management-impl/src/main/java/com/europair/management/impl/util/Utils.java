package com.europair.management.impl.util;


import com.europair.management.api.enums.UTCEnum;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.common.Restriction;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                                        throw new InvalidArgumentException(
                                                "Invalid filter params, operator not valid: " + paramValues[1], e);
                                    }

                                    return Restriction.builder()
                                            .column(entry.getKey().replace(FILTER_PARAM_PREFIX, ""))
                                            .value(paramValues[0])
                                            .operator(operator)
                                            .build();
                                }).collect(Collectors.toList()));
            }

            return criteria;

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidArgumentException("Invalid filter params. ", e);
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




}
