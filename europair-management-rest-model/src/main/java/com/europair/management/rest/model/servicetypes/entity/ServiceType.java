package com.europair.management.rest.model.servicetypes.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "service_types")
@Data
public class ServiceType extends AuditModificationBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String code;

  @Column
  private String name;

}
