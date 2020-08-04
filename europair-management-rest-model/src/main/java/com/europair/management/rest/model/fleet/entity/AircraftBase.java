package com.europair.management.rest.model.fleet.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class AircraftBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ToDo: pendiente entidad
    /*
    @ManyToOne
    @JoinColumn(name = "airport_id", nullable = false)
    private Object airport;
     */
    @Column(name = "airport_id")
    private Long airport;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @Column(name = "main_base")
    private Boolean mainBase;
}
