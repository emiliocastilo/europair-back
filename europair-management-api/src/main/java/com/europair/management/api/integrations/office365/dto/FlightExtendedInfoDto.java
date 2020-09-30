package com.europair.management.api.integrations.office365.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlightExtendedInfoDto extends FlightSharingInfoDTO {

    private List<FlightServiceDataDto> services;

}
