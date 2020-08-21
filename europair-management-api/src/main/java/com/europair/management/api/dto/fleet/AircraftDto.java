package com.europair.management.api.dto.fleet;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.dto.operators.OperatorDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("operator")
    private OperatorDTO operator;

    @JsonProperty("aircraftType")
    private AircraftTypeDto aircraftType;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date insuranceEndDate;

    @JsonProperty("ambulance")
    private Boolean ambulance;

    @JsonProperty("daytimeConfiguration")
    private Integer daytimeConfiguration;

    @JsonProperty("seatingF")
    private Integer seatingF;

    @JsonProperty("seatingC")
    private Integer seatingC;

    @JsonProperty("seatingY")
    private Integer seatingY;

    @JsonProperty("nighttimeConfiguration")
    private Integer nighttimeConfiguration;

    @JsonProperty("notes")
    @Size(min = 0, max = TextField.TEXT_255, message = "Field notes must be 255 character max")
    private String notes;

    @JsonProperty("insideUpgradeYear")
    private Integer insideUpgradeYear;

    @JsonProperty("outsideUpgradeYear")
    private Integer outsideUpgradeYear;

    @JsonProperty("observations")
    private List<AircraftObservationDto> observations;
}
