package com.europair.management.api.dto.operators;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDTO extends AuditModificationBaseDTO {

  @JsonProperty("id")
  @Schema(description = "Identifier")
  private Long id;

  @JsonProperty("iataCode")
  @Schema(description = "IATA code", example = "UX")
  @Size(min = 0, max = 3, message = "Field name must be 3 character max")
  private String iataCode;

  @JsonProperty("icaoCode")
  @Schema(description = "ICAO code", example = "U2")
  @Size(min = 0, max = 4, message = "Field name must be 4 character max")
  private String icaoCode;

  @JsonProperty("name")
  @Schema(description = "Operator name", example = "Air Europa")
  @Size(min = 0, max = 255, message = "Field name must be 255 character max")
  private String name;

  @JsonProperty("aocLastRevisionDate")
  private LocalDate aocLastRevisionDate;

  @JsonProperty("aocNumber")
  private String aocNumber;

  @JsonProperty("insuranceExpirationDate")
  private LocalDate insuranceExpirationDate;

}
