package com.europair.management.api.dto.contract;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.PurchaseSaleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private ContractDto contract;

    @NotNull
    @JsonProperty("routeId")
    private Long routeId;

    @JsonProperty("route")
    @EqualsAndHashCode.Exclude
    private RouteDto route;

    @JsonProperty("flightId")
    private Long flightId;

    @JsonProperty("flight")
    @EqualsAndHashCode.Exclude
    private FlightDTO flight;

    @JsonProperty("comments")
    private String comments;

    @NotNull
    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("includedVAT")
    private Boolean includedVAT;

    @NotNull
    @JsonProperty("contractLineType")
    private PurchaseSaleEnum contractLineType;

//    @NotNull
//    @JsonProperty("type")
//    private ServiceTypeEnum type;

}
