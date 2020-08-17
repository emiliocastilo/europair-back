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
public class TerminalDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("airport")
    private AirportDto airport;

    @JsonProperty("code")
    @Size(max = TextField.TEXT_20)
    private String code;

    @JsonProperty("name")
    @Size(max = TextField.NAME)
    private String name;

    @JsonProperty("observation")
    @Size(max = TextField.TEXT_255)
    private String observation;
}
