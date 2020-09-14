package com.europair.management.rest.model.fleet.entity;

import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
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
@Table(name = "aircraft_type_average_speed")
@Data
public class AircraftTypeAverageSpeed extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aircraft_type_id", nullable = false)
    private AircraftType aircraftType;

    @Column(name = "from_distance", nullable = false)
    private Double fromDistance;

    @Column(name = "to_distance", nullable = false)
    private Double toDistance;

    @Column(name = "distance_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit distanceUnit = Unit.NAUTIC_MILE;

    @Column(name = "average_speed", nullable = false)
    private Double averageSpeed;

    @Column(name = "average_speed_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit averageSpeedUnit;

}
