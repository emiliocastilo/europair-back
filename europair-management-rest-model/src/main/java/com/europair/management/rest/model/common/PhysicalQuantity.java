package com.europair.management.rest.model.common;

import lombok.Data;
import org.springframework.boot.convert.PeriodUnit;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class PhysicalQuantity {

    private double magnitude;
    
}
