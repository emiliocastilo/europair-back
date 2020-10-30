package com.europair.management.api.integrations.office365.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AircraftSharingDTO {

    /*Category*/
    @JsonProperty("flightInfo")
    private String categoryCode; // en cotizacion  (¿ solo confirmada ?)
    @JsonProperty("categoryName")
    private String categoryName; // en cotizacion

    /*Type*/
    @JsonProperty("typeCode")   //     private AircraftType aircraftType;
    private String typeCode;
    @JsonProperty("typeName")
    private String typeName;    //      private AircraftType aircraftType;

    /*Subcategory*/
    @JsonProperty("subcategoryCode")
    private String subcategoryCode;   //     private AircraftType aircraftType;
    @JsonProperty("subcategoryName")
    private String subcategoryName; //     private AircraftType aircraftType;


    /* Base Info*/
    @JsonProperty("baseCode")
    private String baseCode;
    @JsonProperty("baseName")
    private String baseName;


}
