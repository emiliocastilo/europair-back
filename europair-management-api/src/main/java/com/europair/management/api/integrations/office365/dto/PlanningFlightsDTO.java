package com.europair.management.api.integrations.office365.dto;

import com.europair.management.api.integrations.office365.enums.Office365PlanningFlightActionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanningFlightsDTO {

    private Office365PlanningFlightActionType actionType;

    @JsonProperty("fileInfo")
    private FileSharingInfoDTO fileSharingInfoDTO;

    @JsonProperty("flightInfo")
    private FlightSharingInfoDTO flightSharingInfoDTO;

}
