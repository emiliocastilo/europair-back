package com.europair.management.api.dto.flights;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.enums.UTCEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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

  @JsonProperty("arrivalTime")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime arrivalTime;

  @JsonProperty("timeZone")
  private UTCEnum timeZone;

  @NotNull
  private Long originId;
  private AirportDto origin;

  @NotNull
  private Long destinationId;
  private AirportDto destination;

  private Integer order;

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

  @JsonProperty("flightNumber")
  private String flightNumber;

  @JsonProperty("slot")
  private String slot;

  @JsonProperty("parking")
  private String parking;
}
