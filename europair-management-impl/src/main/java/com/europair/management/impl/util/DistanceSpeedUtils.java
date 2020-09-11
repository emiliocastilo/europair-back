package com.europair.management.impl.util;

import lombok.Data;

@Data
public class DistanceSpeedUtils {

    private Long originId;
    private Long destinationId;
    private Long aircraftTypeId;
    private Double distance;
    private Double speed;

    public Double getTimeInHours() {
        return (distance != null && speed != null) ? distance / speed : null;
    }

}
