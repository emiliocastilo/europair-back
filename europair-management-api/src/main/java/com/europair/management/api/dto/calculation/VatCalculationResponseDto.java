package com.europair.management.api.dto.calculation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VatCalculationResponseDto {

    @JsonProperty("vatPercentage")
    private Double vatPercentage;

    @JsonProperty("routePercentage")
    private Double routePercentage;

}
