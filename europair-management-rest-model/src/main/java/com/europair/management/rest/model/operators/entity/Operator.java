package com.europair.management.rest.model.operators.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "operators")
@Data
public class Operator extends SoftRemovableBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "iata_code", length = 3)
  private String iataCode;

  @Column(name = "icao_code", length = 4)
  private String icaoCode;

  @Column(length = TextField.TEXT_255)
  private String name;

  @Column(name = "aoc_last_revision_date")
  private LocalDate aocLastRevisionDate;

  @Column(name = "aoc_number", length = 50)
  private String aocNumber;

  @Column(name = "insurance_expiration_date")
  private LocalDate insuranceExpirationDate;

  @OneToMany(mappedBy = "operator")
  private Set<OperatorsAirports> operatorsCertifications;

}
