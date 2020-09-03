package com.europair.management.api.dto.flights;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlightDTO extends AuditModificationBaseDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("departureTime")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime departureTime;

  @JsonProperty("origin")
  @Size(max = 3) // IATA code
  private String origin;

  @JsonProperty("destination")
  @Size(max = 3) // IATA code
  private String destination;

  @JsonProperty("seatsF")
  private Integer seatsF;

  @JsonProperty("seatsC")
  private Integer seatsC;

  @JsonProperty("seatsY")
  private Integer seatsY;

  @JsonProperty("beds")
  private Integer beds;

  @JsonProperty("stretchers")
  private Integer stretchers;

}
