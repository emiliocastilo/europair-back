package com.europair.management.api.dto.contribution;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.ContributionStates;
import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.ExchangeBuyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContributionDTO {

    private Long id;

    private Long fileId;

    private FileDTO file;

    private Long routeId;

    private RouteDto route;

    private ContributionStates contributionState;

    private Long operatorId;

    private OperatorDTO operator;

    private Long aircraftId;

    private AircraftDto aircraft;

    private LocalDate requestTime;

    private LocalDate quotedTime;

    // maximum load who must be the airborne to the destiny
    private Long cargoAirborne;

    private CurrencyEnum currency;

    private String comments;

    private ExchangeBuyTypeEnum exchangeBuyType;

    private BigDecimal purchasePrice;

    private Integer purchaseCommissionPercent;

    private Boolean includedIva;

    private BigDecimal salesPrice;

    private Integer salesCommissionPercent;

    private BigDecimal salesPricewithoutIVA;

}
