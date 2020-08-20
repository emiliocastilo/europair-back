package com.europair.management.api.dto.airport;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.common.MeasureDto;
import com.europair.management.api.dto.common.TextField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunwayDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @Size(max = TextField.NAME)
    private String name;

    @JsonProperty("mainRunway")
    private Boolean mainRunway;

    @JsonProperty("length")
    private MeasureDto length;

    @JsonProperty("width")
    private MeasureDto width;

    @JsonProperty("observation")
    @Size(max = TextField.TEXT_255)
    private String observation;
}
