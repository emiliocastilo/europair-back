package com.europair.management.api.dto.routes;

import com.europair.management.api.enums.FrequencyEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @NotNull
    private LocalDate startDate;

    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

}
