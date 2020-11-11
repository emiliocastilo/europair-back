package com.europair.management.api.dto.contribution;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.LineContributionRouteType;
import com.europair.management.api.enums.ServiceTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineContributionRouteDTO {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("contributionId")
    private Long contributionId;

    @JsonProperty("contribution")
    private ContributionDTO contribution;

    @NotNull
    @JsonProperty("routeId")
    private Long routeId;

    @JsonProperty("route")
    private RouteDto route;

    @JsonProperty("flightId")
    private Long flightId;

    @JsonProperty("flight")
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
    @JsonProperty("lineContributionRouteType")
    private LineContributionRouteType lineContributionRouteType;

    @NotNull
    @JsonProperty("type")
    private ServiceTypeEnum type;

}
