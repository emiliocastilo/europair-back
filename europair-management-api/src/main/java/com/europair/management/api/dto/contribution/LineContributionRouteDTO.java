package com.europair.management.api.dto.contribution;

import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.*;
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

    @JsonProperty("comments")
    private String comments;

    @NotNull
    @JsonProperty("purchasePrice")
    private BigDecimal price;

    @JsonProperty("includedVAT")
    private Boolean includedVAT;

    @NotNull
    @JsonProperty("lineContributionRouteType")
    private LineContributionRouteType lineContributionRouteType;

    @NotNull
    @JsonProperty("type")
    private ServiceTypeEnum type;

}
