package com.europair.management.api.integrations.office365.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfirmedOperationDto implements Serializable {

//    private FileSharingInfoDTO fileInfo;

//    private List<FlightSharingInfoDTO> flightsInfo;

    private ContributionDataDto contributionInfo;

    private String observations;
}
