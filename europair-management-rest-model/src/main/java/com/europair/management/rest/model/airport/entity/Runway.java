package com.europair.management.rest.model.airport.entity;

import com.europair.management.rest.model.common.PhysicalQuantity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "runways")
@Data
public class Runway implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Airport airport;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "magnitude", column = @Column(name = "length")),
            @AttributeOverride(name = "unit", column = @Column(name = "length_unit"))
    })
    private PhysicalQuantity length;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "magnitude", column = @Column(name = "width")),
            @AttributeOverride(name = "unit", column = @Column(name = "width_unit"))
    })
    private PhysicalQuantity width;

    private String comments;
}
