package com.europair.management.api.dto.operatorsairports;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.operators.OperatorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorsAirportsDTO extends AuditModificationBaseDTO {

  @JsonProperty("id")
  @Schema(description = "Identifier")
  private Long id;

  @JsonProperty("operator")
  private OperatorDTO operator;

  @JsonProperty("airport")
  private AirportDto airport;

  @JsonProperty("comments")
  private String comments;

}
