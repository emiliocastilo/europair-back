package com.europair.management.rest.model.files.entity;

import com.europair.management.api.enums.OperationTypeEnum;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntityHardAudited;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.users.entity.User;
import lombok.Data;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * This entity refers to Expediente in Spanish. This is a direct request of the Product Owner.
 */
@DynamicUpdate
@Entity
@Table(name = "files")
@Data
@Audited
public class File extends SoftRemovableBaseEntityHardAudited implements Serializable, Diffable<File> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(nullable = false)
  private String code;

  @NotNull
  @Column(nullable = false)
  private String description;

  @NotNull
  @Column(name = "status_id", nullable = false)
  private Long statusId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
  private FileStatus status;

  @NotNull
  @Column(name = "client_Id", nullable = false)
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

  // TODO: review - if this attribute must be required
  @Column(name = "provider_id",nullable = false)
  private Long providerId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "provider_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
  private Provider provider; // List ???

  @Column(name = "sale_person_id")
  private Long salePersonId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "sale_person_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
  private User salePerson;

  @Column(name = "sale_agent_id")
  private Long saleAgentId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "sale_agent_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
  private User saleAgent;

  @NotNull
  @Column(name = "operation_type")
  @Enumerated(EnumType.STRING)
  private OperationTypeEnum operationType;

  @NotAudited
  @OneToMany(orphanRemoval = true, mappedBy = "file")
  private List<Route> routes;

  @Column(name = "file_updated_after_contract_signed")
  private Boolean updatedAfterContractSigned;

  @Override
  public DiffResult<File> diff(File file) {
    return new DiffBuilder<>(this, file, ToStringStyle.DEFAULT_STYLE)
            .append("id", this.id, file.id)
            .append("code", this.code, file.code)
            .append("description", this.description, file.description)
            .append("statusId", this.statusId, file.statusId)
            .append("clientId", this.clientId, file.clientId)
            .append("contactId", this.contactId, file.contactId)
            .append("providerId", this.providerId, file.providerId)
            .append("salePersonId", this.salePersonId, file.salePersonId)
            .append("saleAgentId", this.saleAgentId, file.saleAgentId)
            .append("operationType", this.operationType, file.operationType)
            .append("updatedAfterContractSigned", this.updatedAfterContractSigned, file.updatedAfterContractSigned)
            .append("removesAt", this.getRemovedAt(), file.getRemovedAt())
            .build();
  }
}
