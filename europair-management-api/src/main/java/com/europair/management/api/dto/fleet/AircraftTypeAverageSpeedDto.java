package com.europair.management.api.dto.fleet;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.conversions.common.Unit;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftTypeAverageSpeedDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fromDistance")
    @NotNull
    private Double fromDistance;

    @JsonProperty("toDistance")
    @NotNull
    private Double toDistance;

    @JsonProperty("distanceUnit")
    @NotNull
    private Unit distanceUnit;

    @JsonProperty("averageSpeed")
    @NotNull
    private Double averageSpeed;

    @JsonProperty("averageSpeedUnit")
    @NotNull
    private Unit averageSpeedUnit;

}
