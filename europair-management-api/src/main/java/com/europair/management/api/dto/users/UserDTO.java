package com.europair.management.api.dto.users;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.roles.RoleDTO;
import com.europair.management.api.dto.tasks.TaskDTO;
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

  private String password;

  private String name;

  private String surname;

  private String email;

  private String timeZone;

  private List<RoleDTO> roles;

  private List<TaskDTO> tasks;

}
