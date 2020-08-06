package com.europair.management.rest.model.operators.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
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

  @Column(name = "iata_code")
  private String iataCode;

  @Column(name = "icao_code")
  private String icaoCode;

  @Column
  private String name;

  @Column
  private LocalDate aocLastRevisionDate;

  @Column
  private String aocNumber;

  @Column
  private LocalDate insuranceExpirationDate;

  @OneToMany(mappedBy = "operator")
  private List<Certification> certifications;

  @OneToMany(mappedBy = "operator")
  private List<OperatorComment> comments;

  //private List<Contact> associatedContacts;

  //private Fleet fleet;

}
