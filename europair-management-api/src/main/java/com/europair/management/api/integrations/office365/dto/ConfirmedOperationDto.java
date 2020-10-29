package com.europair.management.api.integrations.office365.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ConfirmedOperationDto implements Serializable {

    private FileSharingExtendedInfoDto fileInfo;

    private List<FlightExtendedInfoDto> flightsInfo;

    private ContributionDataDto contributionInfo;

}
