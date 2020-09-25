package com.europair.management.api.dto.services;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.enums.ServiceTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("code")
    @Size(min = 1, max = TextField.TEXT_20)
    @NotEmpty
    private String code;

    @JsonProperty("name")
    @Size(min = 1, max = TextField.TEXT_255)
    @NotEmpty
    private String name;

    @JsonProperty("type")
    @NotNull
    private ServiceTypeEnum type;

}
