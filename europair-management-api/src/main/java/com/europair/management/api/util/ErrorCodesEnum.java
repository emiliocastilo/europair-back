package com.europair.management.api.util;

import org.springframework.http.HttpStatus;

/**
 * Enum to be used to parse error codes for translations
 */
public enum ErrorCodesEnum {

    // Airports
    AIRPORT_NOT_FOUND("101", HttpStatus.NOT_FOUND, "Airport not found with id");


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
