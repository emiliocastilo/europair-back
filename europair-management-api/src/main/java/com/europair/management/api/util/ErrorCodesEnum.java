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

    // File
    FILE_NOT_FOUND("160", HttpStatus.NOT_FOUND, "File not found with id"),
    FILE_NEW_WITH_ID("161", HttpStatus.BAD_REQUEST, "New File expected. Found identifier"),

    // Taxes
    TAX_NOT_FOUND("170", HttpStatus.NOT_FOUND, "No Tax found for code"),

    // City
    CITY_NOT_FOUND("180", HttpStatus.NOT_FOUND, "City not found with id"),

    // Contract Cancel fee
    CONTRACT_CANCEL_FEE_NOT_FOUND("190", HttpStatus.NOT_FOUND, "ContractCancelFee not found with id"),
    CONTRACT_CANCEL_FEE_NEW_WITH_ID("191", HttpStatus.BAD_REQUEST, "New ContractCancelFee expected. Found identifier"),

    // Contract Condition
    CONTRACT_CONDITION_NOT_FOUND("200", HttpStatus.NOT_FOUND, "ContractCondition not found with id"),
    CONTRACT_CONDITION_NEW_WITH_ID("201", HttpStatus.BAD_REQUEST, "New ContractCondition expected. Found identifier"),
    CONTRACT_CONDITION_NOT_FOUND_MULTIPLE("202", HttpStatus.PRECONDITION_FAILED, "Not found conditions with ids"),

    // Contract
    CONTRACT_NOT_FOUND("210", HttpStatus.NOT_FOUND, "Contract not found with id"),
    CONTRACT_NEW_WITH_ID("211", HttpStatus.BAD_REQUEST, "New Contract expected. Found identifier"),
    CONTRACT_GENERATION_FAIL("212", HttpStatus.PRECONDITION_FAILED, "Cannot generate contracts. All the routes have to be in state WON."),
    CONTRACT_SIGNED_MODIFICATION("213", HttpStatus.PRECONDITION_FAILED, "The contract is signed and can't be modified."),

    // Contract Configuration
    CONTRACT_CONFIGURATION_NOT_FOUND("220", HttpStatus.NOT_FOUND, "No configuration found for contract with id"),
    CONTRACT_CONFIGURATION_NEW_WITH_ID("221", HttpStatus.BAD_REQUEST, "New ContractConfiguration expected. Found identifier"),
    CONTRACT_CONFIGURATION_MISMATCH("222", HttpStatus.BAD_REQUEST, "No configuration matches the contract, provided ids"),

    // Contract Payment Condition
    CONTRACT_PAYMENT_CONDITION_NOT_FOUND("230", HttpStatus.NOT_FOUND, "ContractPaymentCondition not found with id"),
    CONTRACT_PAYMENT_CONDITION_NEW_WITH_ID("231", HttpStatus.BAD_REQUEST, "New ContractPaymentCondition expected. Found identifier"),

    // Contract Line
    CONTRACT_LINE_NOT_FOUND("240", HttpStatus.NOT_FOUND, "ContractLine not found with id"),
    CONTRACT_LINE_CONTRACT_MISMATCH("241", HttpStatus.BAD_REQUEST, "Selected contract line doesn't match for the contract with id"),

    // Contribution
    CONTRIBUTION_NOT_FOUND("250", HttpStatus.NOT_FOUND, "Contribution not found with id"),
    CONTRIBUTION_NEW_WITH_ID("251", HttpStatus.BAD_REQUEST, "New Contribution expected. Found identifier"),
    CONTRIBUTION_ROUTE_NOT_FOUND("252", HttpStatus.BAD_REQUEST, "Not found in contribution route with id"),
    CONTRIBUTION_LINE_NOT_FOUND("253", HttpStatus.NOT_FOUND, "Contribution line not found with id"),
    CONTRIBUTION_LINE_CONTRIBUTION_MISMATCH("254", HttpStatus.BAD_REQUEST, "The given contribution id do not match with the related contribution id in the Contribution Line provided."),
    CONTRIBUTION_LINE_ROUTE_MISMATCH("255", HttpStatus.UNPROCESSABLE_ENTITY, "Contribution lines doesn't match with route with id"),
    CONTRIBUTION_LINE_SALE_LINE_NOT_FOUND("256", HttpStatus.PRECONDITION_FAILED, "No route contribution sale line found for rotation with id"),

    // Route
    ROUTE_NOT_FOUND("260", HttpStatus.NOT_FOUND, "Route not found with id"),
    ROUTE_NEW_WITH_ID("261", HttpStatus.BAD_REQUEST, "New Route expected. Found identifier"),
    ROUTE_WITH_NO_ROTATIONS("262", HttpStatus.PRECONDITION_FAILED, "No rotations found for route with id"),
    ROUTE_FIRST_FLIGHT_NOT_FOUND("263", HttpStatus.NOT_FOUND, "No first flight found for route with id"),
    ROUTE_LAST_FLIGHT_NOT_FOUND("264", HttpStatus.NOT_FOUND, "No last flight found for route with id"),

    // Country
    COUNTRY_NOT_FOUND("270", HttpStatus.NOT_FOUND, "Country not found with id"),

    // Client
    CLIENT_NOT_FOUND("280", HttpStatus.NOT_FOUND, "Client not found with id"),
    CLIENT_NEW_WITH_ID("281", HttpStatus.BAD_REQUEST, "New Client expected. Found identifier"),

    // Contact
    CONTACT_NOT_FOUND("290", HttpStatus.NOT_FOUND, "Contact not found with id"),
    CONTACT_NEW_WITH_ID("291", HttpStatus.BAD_REQUEST, "New Contact expected. Found identifier"),

    // File Additional Data
    FILE_ADDITIONAL_DATA_NOT_FOUND("300", HttpStatus.NOT_FOUND, "FileAdditionalData not found with id"),
    FILE_ADDITIONAL_DATA_NEW_WITH_ID("301", HttpStatus.BAD_REQUEST, "New FileAdditionalData expected. Found identifier"),

    // File Status
    FILE_STATUS_NOT_FOUND("300", HttpStatus.NOT_FOUND, "FileStatus not found with id"),
    FILE_STATUS_NEW_WITH_ID("301", HttpStatus.BAD_REQUEST, "New FileStatus expected. Found identifier"),
    FILE_STATUS_CODE_NOT_FOUND("302", HttpStatus.NOT_FOUND, "FileStatus not found with code"),

    // Provider
    PROVIDER_NOT_FOUND("310", HttpStatus.NOT_FOUND, "Provider not found with id"),
    PROVIDER_NEW_WITH_ID("311", HttpStatus.BAD_REQUEST, "New Provider expected. Found identifier"),

    // Aircraft Base
    AIRCRAFT_BASE_NOT_FOUND("320", HttpStatus.NOT_FOUND, "AircraftBase not found with id"),
    AIRCRAFT_BASE_NEW_WITH_ID("321", HttpStatus.BAD_REQUEST, "New AircraftBase expected. Found identifier"),

    // Aircraft
    AIRCRAFT_NOT_FOUND("330", HttpStatus.NOT_FOUND, "Aircraft not found with id"),
    AIRCRAFT_NEW_WITH_ID("331", HttpStatus.BAD_REQUEST, "New Aircraft expected. Found identifier"),

    // Aircraft Category
    AIRCRAFT_CATEGORY_NOT_FOUND("340", HttpStatus.NOT_FOUND, "AircraftCategory not found with id"),
    AIRCRAFT_CATEGORY_NEW_WITH_ID("341", HttpStatus.BAD_REQUEST, "New AircraftCategory expected. Found identifier"),
    AIRCRAFT_SUBCATEGORY_NOT_FOUND("342", HttpStatus.NOT_FOUND, "Aircraft subcategory not found with id"),
    AIRCRAFT_SUBCATEGORY_NEW_WITH_ID("343", HttpStatus.BAD_REQUEST, "New Aircraft subcategory expected. Found identifier"),

    // Aircraft Category
    AIRCRAFT_OBSERVATION_NOT_FOUND("350", HttpStatus.NOT_FOUND, "AircraftObservation not found with id"),
    AIRCRAFT_OBSERVATION_NEW_WITH_ID("351", HttpStatus.BAD_REQUEST, "New AircraftObservation expected. Found identifier"),

    // Flight
    FLIGHT_NOT_FOUND("360", HttpStatus.NOT_FOUND, "Flight not found with id"),
    FLIGHT_NEW_WITH_ID("361", HttpStatus.BAD_REQUEST, "New Flight expected. Found identifier"),

    // Aircraft Type
    AIRCRAFT_TYPE_NOT_FOUND("370", HttpStatus.NOT_FOUND, "AircraftType not found with id"),
    AIRCRAFT_TYPE_NEW_WITH_ID("371", HttpStatus.BAD_REQUEST, "New AircraftType expected. Found identifier"),

    // Aircraft Type Observation
    AIRCRAFT_TYPE_OBSERVATION_NOT_FOUND("380", HttpStatus.NOT_FOUND, "AircraftTypeObservation not found with id"),
    AIRCRAFT_TYPE_OBSERVATION_NEW_WITH_ID("381", HttpStatus.BAD_REQUEST, "New AircraftTypeObservation expected. Found identifier"),

    // Aircraft Type Average Speed
    AIRCRAFT_TYPE_AVG_SPEED_NOT_FOUND("390", HttpStatus.NOT_FOUND, "AircraftTypeAverageSpeed not found with id"),
    AIRCRAFT_TYPE_AVG_SPEED_NEW_WITH_ID("391", HttpStatus.BAD_REQUEST, "New AircraftTypeAverageSpeed expected. Found identifier"),

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
