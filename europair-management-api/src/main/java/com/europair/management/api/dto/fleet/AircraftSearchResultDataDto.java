package com.europair.management.api.dto.fleet;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftSearchResultDataDto {

    // Aircraft fields

    @JsonProperty("aircraftId")
    private Long aircraftId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("seatingF")
    private Integer seatingF;

    @JsonProperty("seatingC")
    private Integer seatingC;

    @JsonProperty("seatingY")
    private Integer seatingY;

    @JsonProperty("daytimeConfiguration")
    private Integer daytimeConfiguration;

    @JsonProperty("nighttimeConfiguration")
    private Integer nighttimeConfiguration;

    @JsonProperty("observations")
    private List<AircraftObservationDto> observations;

    @JsonProperty("insuranceEndDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date insuranceEndDate;

    @JsonProperty("timeInHours")
    private Double timeInHours;

    @JsonProperty("connectionFlights")
    private Integer connectionFlights;


    // Bases

    @JsonProperty("mainBaseId")
    private Long mainBaseId;

    @JsonProperty("mainBaseName")
    private String mainBaseName;


    // Operator fields

    @JsonProperty("operatorId")
    private Long operatorId;

    @JsonProperty("operatorName")
    private String operatorName;

    @JsonProperty("operatorAocLastRevisionDate")
    private LocalDate operatorAocLastRevisionDate;

    @JsonProperty("operatorInsuranceExpirationDate")
    private LocalDate operatorInsuranceExpirationDate;


    // Aircraft Type fields

    @JsonProperty("aircraftTypeId")
    private Long aircraftTypeId;

    @JsonProperty("aircraftTypeDescription")
    private String aircraftTypeDescription;

    @JsonProperty("maxCargo")
    private Double maxCargo;


    // Aircraft Category

    @JsonProperty("aircraftCategoryId")
    private Long aircraftCategoryId;

    @JsonProperty("aircraftCategoryCode")
    private String aircraftCategoryCode;


    // Aircraft Subcategory

    @JsonProperty("aircraftSubcategoryId")
    private Long aircraftSubcategoryId;

    @JsonProperty("aircraftSubcategoryCode")
    private String aircraftSubcategoryCode;

}
