package com.europair.management.api.dto.operators;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatorCommentDTO extends AuditModificationBaseDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("comment")
  private String comment;

}
