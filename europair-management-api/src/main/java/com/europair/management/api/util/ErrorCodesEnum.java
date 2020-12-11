package com.europair.management.api.util;

import org.springframework.http.HttpStatus;

/**
 * Enum to be used to parse error codes for translations
 */
public enum ErrorCodesEnum {

    // Airport
    AIRPORT_NOT_FOUND("100", HttpStatus.NOT_FOUND, "Airport not found with id"),
    AIRPORT_NEW_WITH_ID("101", HttpStatus.BAD_REQUEST, "New Airport expected. Found identifier"),

    // Airport Observation
    AIRPORT_OBSERVATION_NOT_FOUND("110", HttpStatus.NOT_FOUND, "AirportObservation not found with id"),
    AIRPORT_OBSERVATION_NEW_WITH_ID("111", HttpStatus.BAD_REQUEST, "New AirportObservation expected. Found identifier"),

    // Airport Operator
    AIRPORT_OPERATOR_NOT_FOUND("120", HttpStatus.NOT_FOUND, "Operators Certificated not found with id"),
    AIRPORT_OPERATOR_NEW_WITH_ID("121", HttpStatus.BAD_REQUEST, "New Operators Certificated expected. Found identifier"),

    // Region
    REGION_NOT_FOUND("130", HttpStatus.NOT_FOUND, "Region not found with id"),
    REGION_AIRPORT_NOT_FOUND("132", HttpStatus.NOT_FOUND, "Region-Airport not found with id"),

    // Runway
    RUNWAY_NOT_FOUND("140", HttpStatus.NOT_FOUND, "Airport not found with id"),
    RUNWAY_NEW_WITH_ID("141", HttpStatus.BAD_REQUEST, "New Airport expected. Found identifier"),

    // Runway
    TERMINAL_NOT_FOUND("150", HttpStatus.NOT_FOUND, "Terminal not found with id"),
    TERMINAL_NEW_WITH_ID("151", HttpStatus.BAD_REQUEST, "New Terminal expected. Found identifier"),

    ;


    private String code;
    private HttpStatus httpStatus;
    private String description;

    ErrorCodesEnum(String code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ErrorCodesEnum{" +
                "code='" + code + '\'' +
                ", httpStatus=" + httpStatus +
                ", description='" + description + '\'' +
                '}';
    }
}
