package com.europair.management.rest.model.operatorscertifications.entity;

import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.operators.entity.Operator;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "operators_airports")
public class OperatorsAirports extends AuditModificationBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "operator_id")
  private Operator operator;

  @ManyToOne
  @JoinColumn(name = "airport_id")
  private Airport airport;

  @Column
  private String comments;



}
