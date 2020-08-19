package com.europair.management.rest.model.fleet.entity;

import com.europair.management.rest.model.airport.entity.Airport;
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
@Table(name = "aircraft_bases")
@Data
public class AircraftBase extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airport_id", nullable = false)
    private Airport airport;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @Column(name = "main_base")
    private Boolean mainBase;
}
