package com.europair.management.rest.model.operators.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.roles.entity.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "certifications")
@Data
public class Certification extends AuditModificationBaseEntity implements Serializable {
  // TODO: borrar y su DTO

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @ManyToOne(fetch = FetchType.LAZY, optional = false)
//  @JoinColumn(name = "airport_id", nullable = false)
//  private Airport airport;

  @Column
  private String comment;



}
