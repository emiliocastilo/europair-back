package com.europair.management.api.dto.airport;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.common.TextField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportObservationDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("airport")
    private AirportDto airport;

    @JsonProperty("observation")
    @Size(min = 1, max = TextField.TEXT_255)
    @NotEmpty
    private String observation;
}
