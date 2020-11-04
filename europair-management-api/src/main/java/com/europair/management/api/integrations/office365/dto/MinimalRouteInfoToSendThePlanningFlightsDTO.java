package com.europair.management.api.integrations.office365.dto;

import com.europair.management.api.integrations.office365.enums.Office365PlanningFlightActionType;
import lombok.Data;

@Data
public class MinimalRouteInfoToSendThePlanningFlightsDTO {

    private Long routeId;

    private Long contributionId;

    private Office365PlanningFlightActionType actionType;

}
