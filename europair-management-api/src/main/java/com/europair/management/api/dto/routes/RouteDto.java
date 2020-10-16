package com.europair.management.api.dto.routes;

import com.europair.management.api.enums.FrequencyEnum;
import com.europair.management.api.enums.RouteStates;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("label")
    @NotEmpty
    private String label;

    @JsonProperty("frequency")
    private FrequencyEnum frequency;

    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull
    private LocalDate startDate;

    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull
    private LocalDate endDate;

    @JsonProperty("rotationsNumber")
    private Integer rotationsNumber;

    @JsonProperty("rotations")
    private List<RouteDto> rotations;

    @JsonProperty("frequencyDays")
    private List<RouteFrequencyDayDto> frequencyDays;

    @JsonProperty("hasContributions")
    private Boolean hasContributions = false;

    @JsonProperty("routeState")
    private RouteStates routeState;
}
