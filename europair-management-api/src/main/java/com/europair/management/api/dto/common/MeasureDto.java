package com.europair.management.api.dto.common;

import com.europair.management.api.dto.conversions.common.Unit;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MeasureDto {

    @NotNull
    private Long value;

    @NotNull
    private Unit type;
}
