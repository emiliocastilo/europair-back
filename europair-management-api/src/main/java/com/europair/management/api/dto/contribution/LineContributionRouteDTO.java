package com.europair.management.api.dto.contribution;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @JsonProperty("typeLineContributionRoute")
    private TypeLineContributionRoute typeLineContributionRoute;

    @NotNull
    @JsonProperty("type")
    private ServiceTypeEnum type;

}
