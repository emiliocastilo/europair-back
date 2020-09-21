package com.europair.management.rest.model.files.entity;

import com.europair.management.api.enums.OperationTypeEnum;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.users.entity.User;
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

  @Column(name = "status_id")
  private Long statusId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
  private FileStatus status;

  @Column(name = "client_Id")
  private Long clientId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
  private Client client;

  @Column(name = "contact_id")
  private Long contactId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "contact_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
  private Contact contact;

  @Column(name = "provider_id")
  private Long providerId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "provider_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
  private Provider provider; // List ???

  @Column(name = "sale_person_id")
  private Long salePersonId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "sale_person_id", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
  private User salePerson;

  @Column(name = "sale_agent_id")
  private Long saleAgentId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "sale_agent_id", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
  private User saleAgent;

  @Column(name = "operation_type")
  @Enumerated(EnumType.STRING)
  private OperationTypeEnum operationType;

  @NotAudited
  @OneToMany(orphanRemoval = true, mappedBy = "file")
  private List<Route> routes;

}
