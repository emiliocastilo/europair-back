package com.europair.management.rest.model.fleet.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "aircraft_type_observation")
@Data
public class AircraftTypeObservation extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aircraft_type_id", nullable = false)
    private AircraftType aircraftType;

    @Column(name = "observation", nullable = false)
    private String observation;
}
