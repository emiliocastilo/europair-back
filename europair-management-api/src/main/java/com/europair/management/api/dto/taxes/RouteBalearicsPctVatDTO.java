package com.europair.management.api.dto.taxes;

import com.europair.management.api.dto.airport.AirportDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RouteBalearicsPctVatDTO implements Serializable {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("originAirport")
  private AirportDto originAirport;

  @JsonProperty("destinationAirport")
  private AirportDto destinationAirport;

  @JsonProperty("percentage")
  private Double percentage;

}
