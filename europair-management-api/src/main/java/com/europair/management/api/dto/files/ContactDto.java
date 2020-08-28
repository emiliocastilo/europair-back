package com.europair.management.api.dto.files;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.common.TextField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto extends AuditModificationBaseDTO {

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
}
