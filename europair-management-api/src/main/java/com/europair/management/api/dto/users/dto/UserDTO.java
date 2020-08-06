package com.europair.management.api.dto.users.dto;

import com.europair.management.api.dto.audit.dto.AuditModificationBaseDTO;
import com.europair.management.api.dto.roles.dto.RoleDTO;
import com.europair.management.api.dto.tasks.dto.TaskDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO extends AuditModificationBaseDTO {

  private Long id;

  private String username;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  private String name;

  private String surname;

  private String email;

  private String timeZone;

  private List<RoleDTO> roles;

  private List<TaskDTO> tasks;

}
