package com.europair.management.api.integrations.office365.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileSharingExtendedInfoDto extends FileSharingInfoDTO {

    // Additional data
    private String flightMotive;
    private String connections;
    private String limitations;
    private String fixedVariableFuel;
    private String luggage;
    private String specialLuggage;
    private String onBoardService;
    private String specialRequests;
    private String otherCharges;
    private String operationalInfo;

}
