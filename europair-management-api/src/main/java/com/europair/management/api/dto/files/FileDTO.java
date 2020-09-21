package com.europair.management.api.dto.files;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.users.UserDTO;
import com.europair.management.api.enums.OperationTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

  @JsonProperty("statusId")
  private Long statusId;

  @JsonProperty("status")
  private FileStatusDto status;

  @JsonProperty("clientId")
  private Long clientId;

  @JsonProperty("client")
  private ClientDto client;

  @JsonProperty("contactId")
  private Long contactId;

  @JsonProperty("contact")
  private ContactDto contact;

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

  @JsonProperty("operationType")
  private OperationTypeEnum operationType;

  //This entity has his own endpoint to ask for a list of routes of a file, no needed
  //private List<Route> routes;

}
