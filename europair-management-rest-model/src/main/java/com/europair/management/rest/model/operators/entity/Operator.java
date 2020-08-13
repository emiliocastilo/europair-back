package com.europair.management.rest.model.operators.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.common.TextField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "operators")
@Data
public class Operator extends AuditModificationBaseEntity implements Serializable {

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

  @Column(name = "aoc_number")
  private String aocNumber;

  @Column(name = "insurance_expiration_date")
  private LocalDate insuranceExpirationDate;

  @OneToMany(mappedBy = "operator")
  private List<Certification> certifications;

//  @OneToMany(mappedBy = "operator")
//  private List<OperatorComment> comments;

}
