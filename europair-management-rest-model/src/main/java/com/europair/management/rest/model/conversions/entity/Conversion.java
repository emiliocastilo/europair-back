package com.europair.management.rest.model.conversions.entity;

import com.europair.management.api.dto.conversions.common.Unit;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conversions")
@Data
public class Conversion implements Serializable {
//doc
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Unit srcUnit;

    @Enumerated(EnumType.STRING)
    private Unit dstUnit;

    private double factor;
}
