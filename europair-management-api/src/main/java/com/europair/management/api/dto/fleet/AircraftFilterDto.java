package com.europair.management.api.dto.fleet;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftFilterDto extends AuditModificationBaseDTO {

    @JsonProperty("operator")
    private Long operator;

    @JsonProperty("aircraftType")
    private Long aircraftType;

    @JsonProperty("airport")
    private Long airport;

    @JsonProperty("plateNumber")
    private String plateNumber;

    @JsonProperty("productionYear")
    private Integer productionYear;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("insuranceEndDate")
    private Date insuranceEndDate;

    @JsonProperty("ambulance")
    private Boolean ambulance;

    @JsonProperty("daytimeConfiguration")
    private Integer daytimeConfiguration;

    @JsonProperty("nighttimeConfiguration")
    private Integer nighttimeConfiguration;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("insideUpgradeDate")
    private Date insideUpgradeDate;

    @JsonProperty("outsideUpgradeDate")
    private Date outsideUpgradeDate;
}
