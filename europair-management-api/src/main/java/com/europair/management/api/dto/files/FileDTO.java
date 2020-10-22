package com.europair.management.api.dto.files;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.users.UserDTO;
import com.europair.management.api.enums.OperationTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO extends AuditModificationBaseDTO {

  @JsonProperty("id")
  private Long id;

  @NotNull
  @JsonProperty("code")
  private String code;

  @NotNull
  @JsonProperty("description")
  private String description;

  @JsonProperty("statusId")
  private Long statusId;

  @JsonProperty("status")
  private FileStatusDto status;

  @NotNull
  @JsonProperty("clientId")
  private Long clientId;

  @JsonProperty("client")
  private ClientDto client;

  @JsonProperty("contactId")
  private Long contactId;

  @JsonProperty("contact")
  private ContactDto contact;

  // TODO: review - if this attribute must be required in front
  @JsonProperty("providerId")
  private Long providerId;

  @JsonProperty("provider")
  private ProviderDto provider; // List ???

  @JsonProperty("salePersonId")
  private Long salePersonId;

  @JsonProperty("salePerson")
  private UserDTO salePerson;

  @JsonProperty("saleAgentId")
  private Long saleAgentId;

  @JsonProperty("saleAgent")
  private UserDTO saleAgent;

  @NotNull
  @JsonProperty("operationType")
  private OperationTypeEnum operationType;
  
  @JsonProperty("observation")
  @Size(max = 5000)
  private String observation;


  //This entity has his own endpoint to ask for a list of routes of a file, no needed
  //private List<Route> routes;

}
