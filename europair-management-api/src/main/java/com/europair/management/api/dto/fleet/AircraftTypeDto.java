package com.europair.management.api.dto.fleet;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.dto.conversions.common.Unit;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftTypeDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("iataCode")
    @Size(max = TextField.IATA_CODE)
    private String iataCode;

    @JsonProperty("icaoCode")
    @Size(max = TextField.ICAO_CODE)
    private String icaoCode;

    @JsonProperty("code")
    @Size(max = TextField.TEXT_20)
    @NotEmpty
    private String code;

    @JsonProperty("description")
    @Size(max = TextField.TEXT_255)
    @NotEmpty
    private String description;

    @JsonProperty("manufacturer")
    @Size(max = TextField.TEXT_120)
    @NotEmpty
    private String manufacturer;

    @JsonProperty("category")
    @NotNull
    private AircraftCategoryDto category;

    @JsonProperty("subcategory")
    @NotNull
    private AircraftCategoryDto subcategory;

    @JsonProperty("flightRange")
    private Double flightRange;

    @JsonProperty("flightRangeUnit")
    private Unit flightRangeUnit;

    @JsonProperty("cabinWidth")
    private Double cabinWidth;

    @JsonProperty("cabinWidthUnit")
    private Unit cabinWidthUnit;

    @JsonProperty("cabinHeight")
    private Double cabinHeight;

    @JsonProperty("cabinHeightUnit")
    private Unit cabinHeightUnit;

    @JsonProperty("cabinLength")
    private Double cabinLength;

    @JsonProperty("cabinLengthUnit")
    private Unit cabinLengthUnit;

    @JsonProperty("maxCargo")
    private Double maxCargo;

    @JsonProperty("averageSpeed")
    private List<AircraftTypeAverageSpeedDto> averageSpeed;

    @JsonProperty("observations")
    private List<AircraftTypeObservationDto> observations;

}
