package com.europair.management.api.integrations.office365.dto;

import com.europair.management.api.enums.OperationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSharingInfoDTO {

    private OperationTypeEnum operationType;
    private String originAirport;
    private String destinationAirport;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String flightNumber;
    private String operator;
    private String plateNumber;
    private String client;
    private Integer paxTotalNumber;
    private Integer bedsNumber;
    private Integer stretchersNumber;
    private String charge; //TODO: ¿de dónde lo sacamos?


}
