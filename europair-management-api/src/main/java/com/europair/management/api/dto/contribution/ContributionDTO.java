package com.europair.management.api.dto.contribution;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.ExchangeBuyTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private ContributionStatesEnum contributionState;

    @JsonProperty("operatorId")
    private Long operatorId;

    @JsonProperty("operator")
    private OperatorDTO operator;

    @JsonProperty("aircraftId")
    private Long aircraftId;

    @JsonProperty("aircraft")
    private AircraftDto aircraft;

    @JsonProperty("requestTime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime requestTime;

    @JsonProperty("quotedTime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime quotedTime;

    // maximum load who must be the airborne to the destiny
    @JsonProperty("cargoAirborne")
    private Long cargoAirborne;

    @JsonProperty("currency")
    private CurrencyEnum currency;

    @JsonProperty("currencyOnSale")
    private CurrencyEnum currencyOnSale;

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
    private Boolean salesPricewithoutIVA;

}
