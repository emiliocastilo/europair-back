package com.europair.management.api.dto.integration;

import com.europair.management.api.enums.OperationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSharingInfoDTO {

    private OperationTypeEnum operationType;
    private String originAirport;
    private String destinationAirport;
    private LocalDate startDate;
    private LocalDate endDate;
    private String flightNumber;
    private String operator;
    private String plateNumber;
    private String client;
    private Integer paxTotalNumber;
    private Integer bedsNumber;
    private Integer stretchersNumber;
    private String charge; //TODO: ¿de dónde lo sacamos?


}
