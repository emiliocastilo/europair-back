package com.europair.management.api.dto.calculation;

import com.europair.management.api.enums.ServiceTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VatCalculationRequestDto {

    @JsonProperty("fileId")
    private Long fileId;

    @NotNull
    @JsonProperty("originAirportId")
    private Long originAirportId;

    @NotNull
    @JsonProperty("destinationAirportId")
    private Long destinationAirportId;

    @NotNull
    @JsonProperty("serviceType")
    private ServiceTypeEnum serviceType;

    @NotNull
    @JsonProperty("isSale")
    private boolean isSale;
}
