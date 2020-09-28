package com.europair.management.api.dto.fleet;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.api.enums.OperationTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftFilterDto extends AuditModificationBaseDTO {

    @Schema(description = "Identifier of the route(rotation)", required = true)
    @JsonProperty("routeId")
    @NotNull
    private Long routeId;

    @Schema(description = "List of airport ids where the aircraft must have a base", example = "1,2,11", required = true)
    @JsonProperty("bases")
    @NotEmpty
    private List<Long> baseIds;

    @Schema(description = "List of countries ids of the airports where the aircraft must have a base", example = "1,2,11")
    @JsonProperty("countries")
    private List<Long> countryIds;

    @Schema(description = "Minimum number of seats for the daytime config.")
    @JsonProperty("seats")
    private Integer seats;

    @Schema(description = "Minimum number of beds for the nighttime config.")
    @JsonProperty("beds")
    private Integer beds;

    @Schema(description = "Specified category id of the aircraft")
    @JsonProperty("category")
    private Long categoryId;

    @Schema(description = "Specified minimum subcategory id of the aircraft")
    @JsonProperty("subcategory")
    private Long subcategoryId;

    @Schema(description = "Flag to filter only by specified subcategory or superior ones by order")
    @JsonProperty("exactSubcategory")
    private Boolean exactSubcategory;

    @Schema(description = "Flag to filter ambulance aircraft")
    @JsonProperty("ambulance")
    private Boolean ambulance;

    @Schema(description = "Type of operation (OperationTypeEnum)")
    @JsonProperty("operationType")
    private OperationTypeEnum operationType;

    @Schema(description = "List of aircraft type ids to filter", example = "1,2,11")
    @JsonProperty("aircraftTypes")
    private List<Long> aircraftTypes;

    @Schema(description = "List of operator ids to filter", example = "1,2,11")
    @JsonProperty("operators")
    private List<Long> operators;

    @Schema(description = "To search near airports from distance")
    @JsonProperty("fromDistance")
    private Double fromDistance;

    @Schema(description = "To search near airports to distance")
    @JsonProperty("operators")
    private Double toDistance;

    @Schema(description = "To search near airports distance unit")
    @JsonProperty("distanceUnit")
    private Unit distanceUnit;

    @Schema(description = "Specified region id where the aircraft must have some base")
    @JsonProperty("regionId")
    private Long regionId;

    @Schema(description = "Max number of connecting flights to filter")
    @JsonProperty("maxConnectingFlights")
    private Integer maxConnectingFlights;

}
