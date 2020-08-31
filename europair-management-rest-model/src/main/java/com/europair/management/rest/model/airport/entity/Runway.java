package com.europair.management.rest.model.airport.entity;

import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.common.TextField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "runways")
@Data
public class Runway extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airport_id", nullable = false)
    private Airport airport;

    @Column(name = "name", length = TextField.NAME)
    private String name;

    @Column(name = "main_runway")
    private Boolean mainRunway;

    @Column(name = "runway_length")
    private Double length;

    @Column(name = "runway_length_unit", length = TextField.TEXT_20)
    @Enumerated(EnumType.STRING)
    private Unit lengthUnit;

    @Column(name = "runway_width")
    private Double width;

    @Column(name = "runway_width_unit", length = TextField.TEXT_20)
    @Enumerated(EnumType.STRING)
    private Unit widthUnit;

    @Column(name = "observation")
    private String observation;
}
