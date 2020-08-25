package com.europair.management.rest.model.operators.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "operator_comments")
@Data
public class OperatorComment extends AuditModificationBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String comment;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "operator_id", nullable = false)
  private Operator operator;

}
