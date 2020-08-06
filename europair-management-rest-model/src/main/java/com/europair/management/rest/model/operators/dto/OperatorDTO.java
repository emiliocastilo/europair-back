package com.europair.management.rest.model.operators.dto;

import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatorDTO extends AuditModificationBaseDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("iataCode")
  private String iataCode;

  @JsonProperty("icaoCode")
  private String icaoCode;

  @JsonProperty("name")
  private String name;

  @JsonProperty("aocLastRevisionDate")
  private LocalDate aocLastRevisionDate;

  @JsonProperty("aocNumber")
  private String aocNumber;

  @JsonProperty("insuranceExpirationDate")
  private LocalDate insuranceExpirationDate;

  @JsonProperty("certifications")
  private List<CertificationDTO> certifications;

  @JsonProperty("observations")
  private List<OperatorCommentDTO> comments;

  //private List<ContactDTO> associatedContacts;

  //private FleetDTO fleet;
}
