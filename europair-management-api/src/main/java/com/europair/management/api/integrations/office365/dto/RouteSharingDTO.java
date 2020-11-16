package com.europair.management.api.integrations.office365.dto;

import com.europair.management.api.enums.FrequencyEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteSharingDTO {

    @JsonProperty("label")
    private String label;

    @JsonProperty("frequency")
    private FrequencyEnum frequency;

    @JsonProperty("weekDays")
    private List<DayOfWeek> weekDays;

    @JsonProperty("monthDays")
    private List<Integer> monthDays;
}
