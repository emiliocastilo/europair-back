package com.europair.management.rest.model.airport.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "runways")
@Data
public class Runway extends AuditModificationBaseEntity implements Serializable {

}
