package com.europair.management.api.dto.fleet.dto;

import com.europair.management.api.dto.audit.dto.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftBaseDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    // ToDo: pendiente entidad/dto
    private Long airport;

    @JsonProperty("aircraft")
    private AircraftDto aircraft;

    @JsonProperty("mainBase")
    private Boolean mainBase;
}
