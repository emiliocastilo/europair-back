package com.europair.management.api.integrations.office365.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlightExtendedInfoDto extends FlightSharingInfoDTO {

    private List<FlightServiceDataDto> services;

    // Constructor to avoid down-casting

    public FlightExtendedInfoDto(FlightSharingInfoDTO baseDto) {
        super(baseDto.getFlightId(), baseDto.getOperationType(), baseDto.getOriginAirport(), baseDto.getDestinationAirport(),
                baseDto.getStartDate(), baseDto.getEndDate(), baseDto.getLocalStartDate(), baseDto.getLocalEndDate(),
                baseDto.getFlightNumber(), baseDto.getOperator(), baseDto.getPlateNumber(), baseDto.getClient(),
                baseDto.getPaxTotalNumber(), baseDto.getBedsNumber(), baseDto.getStretchersNumber(), baseDto.getCharge());
    }
}
