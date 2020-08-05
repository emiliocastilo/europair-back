package com.europair.management.rest.model.common;

import com.europair.management.rest.common.exception.InvalidArgumentException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static final String FILTER_PARAM_PREFIX = "filter_";
    public static final String FILTER_PARAM_VALUE_SEPARATOR = ",";

    public static CoreCriteria mapFilterRequestParams(Map<String, String> reqParams) {

        try {
            CoreCriteria criteria = new CoreCriteria();
            criteria.setRestrictions(
                    reqParams.entrySet().stream()
                            .filter(entry -> entry.getKey().startsWith(FILTER_PARAM_PREFIX))
                            .map(entry -> {
                                String[] paramValues = entry.getValue().split(FILTER_PARAM_VALUE_SEPARATOR);
                                return Restriction.builder()
                                        .column(entry.getKey().replace(FILTER_PARAM_PREFIX, ""))
                                        .value(paramValues[0])
                                        .operator(OperatorEnum.valueOf(paramValues[1].toUpperCase()))
                                        .build();
                            }).collect(Collectors.toList()));

            return criteria;

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidArgumentException("Invalid filter params. ", e);
        }

    }
}
