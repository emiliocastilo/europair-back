package com.europair.management.api.dto.contribution;

import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.ContributionStates;
import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.ExchangeBuyTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContributionDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fileId")
    private Long fileId;

    @JsonProperty("file")
    private FileDTO file;

    @JsonProperty("routeId")
    private Long routeId;

    @JsonProperty("route")
    private RouteDto route;

    @JsonProperty("contributionState")
    private ContributionStates contributionState;

    @JsonProperty("operatorId")
    private Long operatorId;

    @JsonProperty("operator")
    private OperatorDTO operator;

    @JsonProperty("aircraftId")
    private Long aircraftId;

    @JsonProperty("aircraft")
    private AircraftDto aircraft;

    @JsonProperty("requestTime")
    private LocalDate requestTime;

    @JsonProperty("quotedTime")
    private LocalDate quotedTime;

    // maximum load who must be the airborne to the destiny
    @JsonProperty("cargoAirborne")
    private Long cargoAirborne;

    @JsonProperty("currency")
    private CurrencyEnum currency;

    @JsonProperty("comments")
    private String comments;

    @JsonProperty("exchangeBuyType")
    private ExchangeBuyTypeEnum exchangeBuyType;

    @JsonProperty("purchasePrice")
    private BigDecimal purchasePrice;

    @JsonProperty("purchaseCommissionPercent")
    private Integer purchaseCommissionPercent;

    @JsonProperty("includedIva")
    private Boolean includedIva;

    @JsonProperty("salesPrice")
    private BigDecimal salesPrice;

    @JsonProperty("salesCommissionPercent")
    private Integer salesCommissionPercent;

    @JsonProperty("salesPricewithoutIVA")
    private BigDecimal salesPricewithoutIVA;

}
