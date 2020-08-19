package com.europair.management.api.dto.fleet;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
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

    @JsonProperty("airport")
    private AirportDto airport;

//    @JsonProperty("aircraft")
//    private AircraftDto aircraft;

    @JsonProperty("mainBase")
    private Boolean mainBase;
}
