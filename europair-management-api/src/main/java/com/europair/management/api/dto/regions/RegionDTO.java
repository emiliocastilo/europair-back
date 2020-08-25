package com.europair.management.api.dto.regions;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.countries.CountryDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO extends AuditModificationBaseDTO {

  @JsonProperty("id")
  @Schema(description = "Identifier")
  private Long id;

  @JsonProperty("code")
  @NotBlank(message = "Field code is mandatory")
  @Schema(description = "Region code", example = "PEN")
  @Size(min = 0, max = 4, message = "Field code must be 4 character max")
  private String code;

  @JsonProperty("name")
  @NotBlank(message = "Field name is mandatory")
  @Schema(description = "Region name", example = "Peninsula")
  @Size(min = 0, max = 255, message = "Field name must be 255 character max")
  private String name;

  @JsonProperty("countries")
  @Schema(description = "Associated countries")
  private List<CountryDTO> countries;

  @JsonProperty("airports")
  @Schema(description = "Associated airports")
  private List<AirportDto> airports;

}
