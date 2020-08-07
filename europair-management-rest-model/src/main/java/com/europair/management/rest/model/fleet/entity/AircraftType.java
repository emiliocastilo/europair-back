package com.europair.management.rest.model.fleet.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "aircraft_types")
@Data
public class AircraftType extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
