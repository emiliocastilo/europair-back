package com.europair.management.api.integrations.office365.dto;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseContributionFlights {

    // this wrapper contains information about: fileSharingInfoDTO and flightSharingInfoDTO
    @JsonProperty("planningFlightsDTOS")
    private List<PlanningFlightsDTO> planningFlightsDTOS;

    @JsonProperty("aircraftSharingDTO")
    private AircraftSharingDTO aircraftSharingDTO;

    @JsonProperty("operatorSharingDTO")
    private OperatorSharingDTO operatorSharingDTO;
}
