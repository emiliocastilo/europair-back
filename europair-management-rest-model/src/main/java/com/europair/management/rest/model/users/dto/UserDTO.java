package com.europair.management.rest.model.users.dto;

import com.europair.management.rest.model.roles.dto.RoleDTO;
import com.europair.management.rest.model.tasks.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

  private Long id;

  private String username;

  private String name;

  private String surname;

  private String email;

  private String timeZone;

  private List<RoleDTO> roles;

  private List<TaskDTO> tasks;

}
