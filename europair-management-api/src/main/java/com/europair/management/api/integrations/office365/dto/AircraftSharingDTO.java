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
    private Integer categoryCode; // en cotizacion  (¿ solo confirmada ?)
    @JsonProperty("flightInfo")
    private String categoryName; // en cotizacion

    /*type*/
    @JsonProperty("flightInfo")   //     private AircraftType aircraftType;
    private Integer typeCode;
    @JsonProperty("flightInfo")
    private String typeName;    //      private AircraftType aircraftType;

    /*Subcategory*/
    @JsonProperty("flightInfo")
    private Integer subcategoryCode;   //     private AircraftType aircraftType;
    @JsonProperty("flightInfo")
    private String subcategoryName; //     private AircraftType aircraftType;


    /* */
    @JsonProperty("flightInfo")
    private Integer baseCode;
    @JsonProperty("flightInfo")
    private String baseName;


}
