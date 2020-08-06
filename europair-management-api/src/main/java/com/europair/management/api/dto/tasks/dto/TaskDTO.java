package com.europair.management.api.dto.tasks.dto;

import com.europair.management.api.dto.audit.dto.AuditModificationBaseDTO;
import com.europair.management.api.dto.screens.dto.ScreenDTO;
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
public class TaskDTO extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("screens")
    private List<ScreenDTO> screens;

}
