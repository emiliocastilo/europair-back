package com.europair.management.rest.model.files.entity;

import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.enums.OperationTypeEnum;
import com.europair.management.rest.model.routes.entity.Route;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "files")
@Data
public class File extends SoftRemovableBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String code;

  @Column
  private String description;

  @ManyToOne
  private FileStatus status;

  @ManyToOne
  private Client client;

  @ManyToOne
  private Contact contact;

  @ManyToOne
  private Provider provider; // List ???

  @Column
  private String salePerson;

  @Column
  private String saleAgent;

  @Column(name = "operation_type")
  @Enumerated(EnumType.STRING)
  private OperationTypeEnum operationType;

  @OneToMany(orphanRemoval = true, mappedBy = "file")
  private List<Route> routes;

}
