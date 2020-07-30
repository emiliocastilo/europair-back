package com.europair.management.rest.model.common;

import com.europair.management.rest.model.conversions.common.Unit;
import lombok.Data;
import org.springframework.boot.convert.PeriodUnit;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class PhysicalQuantity {

    private double magnitude;

    private Unit unit;
}
