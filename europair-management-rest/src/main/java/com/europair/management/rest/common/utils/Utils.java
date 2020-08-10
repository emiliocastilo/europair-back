package com.europair.management.rest.common.utils;

import com.europair.management.rest.common.exception.InvalidArgumentException;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.common.Restriction;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static final String FILTER_PARAM_PREFIX = "filter_";
    public static final String FILTER_PARAM_VALUE_SEPARATOR = ",";

    public static CoreCriteria mapFilterRequestParams(Map<String, String> reqParams) {

        try {
            CoreCriteria criteria = new CoreCriteria();

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
}
