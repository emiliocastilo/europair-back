package com.europair.management.rest.model.fleet.dto;

import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import com.europair.management.rest.model.common.TextField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    // ToDo: pendiente entidad/dto
    @JsonProperty("operator")
    private Long operator;

    // ToDo: pendiente entidad/dto
    @JsonProperty("aircraftType")
    private Long aircraftType;

    @JsonProperty("bases")
    private List<AircraftBaseDto> bases;

    @JsonProperty("plateNumber")
    @Size(min = 0, max = TextField.TEXT_20)
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
    @Size(min = 0, max = TextField.TEXT_255, message = "Field notes must be 255 character max")
    private String notes;

    @JsonProperty("insideUpgradeDate")
    private Date insideUpgradeDate;

    @JsonProperty("outsideUpgradeDate")
    private Date outsideUpgradeDate;
}
