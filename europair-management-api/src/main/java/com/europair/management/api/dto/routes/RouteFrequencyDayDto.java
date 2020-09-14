package com.europair.management.api.dto.routes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteFrequencyDayDto implements Serializable {

    private Long id;

    private DayOfWeek weekday;

    private Integer monthDay;

}
