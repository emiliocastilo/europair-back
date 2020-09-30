package com.europair.management.api.integrations.office365.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanningFlightsDTO {

    private String actionType;

    @JsonProperty("fileInfo")
    private FileSharingInfoDTO fileSharingInfoDTO;

    @JsonProperty("flightInfo")
    private FlightSharingInfoDTO flightSharingInfoDTO;

}