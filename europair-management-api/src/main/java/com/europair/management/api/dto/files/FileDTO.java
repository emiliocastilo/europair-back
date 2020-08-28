package com.europair.management.api.dto.files;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO extends AuditModificationBaseDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("code")
  private String code;

  @JsonProperty("description")
  private String description;

  @JsonProperty("status")
  private FileStatusDto status;

  @JsonProperty("client")
  private ClientDto client;

  @JsonProperty("contact")
  private ContactDto contact;

  @JsonProperty("provider")
  private ProviderDto provider; // List ???

  @JsonProperty("salePerson")
  private String salePerson;

  @JsonProperty("saleAgent")
  private String saleAgent;

  @JsonProperty("operationType")
  private String operationType;

}
