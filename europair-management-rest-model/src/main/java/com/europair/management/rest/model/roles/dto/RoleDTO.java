package com.europair.management.rest.model.roles.dto;

import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import com.europair.management.rest.model.tasks.dto.TaskDTO;
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
public class RoleDTO extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("tasks")
    private List<TaskDTO> tasks;

}
