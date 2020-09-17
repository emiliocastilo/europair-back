package com.europair.management.rest.model.files.entity;

import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.enums.OperationTypeEnum;
import com.europair.management.rest.model.routes.entity.Route;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * This entity refers to Expediente in Spanish. This is a direct request of the Product Owner.
 */
@Entity
@Table(name = "files")
@Data
@Audited
public class File extends SoftRemovableBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String code;

  @Column
  private String description;

  @NotAudited
  @ManyToOne
  private FileStatus status;

  @NotAudited
  @ManyToOne
  private Client client;

  @NotAudited
  @ManyToOne
  private Contact contact;

  @NotAudited
  @ManyToOne
  private Provider provider; // List ???

  @Column
  private String salePerson;

  @Column
  private String saleAgent;

  @NotAudited
  @Column(name = "operation_type")
  @Enumerated(EnumType.STRING)
  private OperationTypeEnum operationType;

  @NotAudited
  @OneToMany(orphanRemoval = true, mappedBy = "file")
  private List<Route> routes;

}
