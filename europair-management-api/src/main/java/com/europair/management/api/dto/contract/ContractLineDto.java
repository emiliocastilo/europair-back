package com.europair.management.api.dto.contract;

import com.europair.management.api.dto.contribution.LineContributionRouteDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.PurchaseSaleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractLineDto {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("contractId")
    private Long contractId;

    @JsonProperty("contract")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ContractDto contract;

    @NotNull
    @JsonProperty("routeId")
    private Long routeId;

    @JsonProperty("route")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RouteDto route;

    @JsonProperty("contributionLineId")
    private Long contributionLineId;

    @JsonProperty("contributionLine")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private LineContributionRouteDTO contributionLine;

    @JsonProperty("comments")
    private String comments;

    @JsonProperty("currency")
    private CurrencyEnum currency;

    @NotNull
    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("vatPercentage")
    private Double vatPercentage;

    @JsonProperty("vatAmount")
    private BigDecimal vatAmount;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("includedVAT")
    private Boolean includedVAT;

    @NotNull
    @JsonProperty("contractLineType")
    private PurchaseSaleEnum contractLineType;

}
