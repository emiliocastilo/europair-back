package com.europair.management.rest.model.operators.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "certifications")
@Data
public class Certification extends AuditModificationBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @ManyToOne
//  @JoinColumn(name = "airport_id")
//  private Airport airport;

  @Column
  private String comment;

  @ManyToOne
  @JoinColumn(name = "operator_id")
  private Operator operator;

}