package com.europair.management.api.dto.fleet;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftFilterDto extends AuditModificationBaseDTO {

    @JsonProperty("base")
    @NotNull
    private Long baseId;

    @JsonProperty("seats")
    private Integer seats;

    @JsonProperty("beds")
    private Integer beds;

    @JsonProperty("category")
    private Long subcategoryId;

    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private Date startDate;

    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private Date endDate;

    @JsonProperty("ambulance")
    private Boolean ambulance;

}
