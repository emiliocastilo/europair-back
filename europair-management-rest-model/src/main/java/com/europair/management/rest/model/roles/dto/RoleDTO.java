package com.europair.management.rest.model.roles.dto;

import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import com.europair.management.rest.model.tasks.dto.TaskDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO extends AuditModificationBaseDTO {

    @JsonProperty("id")
    @Schema(description = "Identifier")
    private Long id;

    @JsonProperty("name")
    @NotBlank(message = "Field name is mandatory")
    @Schema(description = "Role name", example = "Administrator")
    @Size(min = 0, max = 255, message = "Field name must be 255 character max")
    private String name;

    @JsonProperty("description")
    @Schema(description = "Role description")
    private String description;

    @JsonProperty("tasks")
    @Schema(description = "Associated tasks")
    private List<TaskDTO> tasks;

}
