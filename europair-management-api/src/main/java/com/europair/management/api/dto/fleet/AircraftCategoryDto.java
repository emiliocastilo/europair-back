package com.europair.management.api.dto.fleet;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.common.TextField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftCategoryDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("code")
    @Size(max = TextField.TEXT_20)
    private String code;

    @JsonProperty("name")
    @Size(max = TextField.TEXT_120)
    private String name;

    @JsonProperty("order")
    private Integer order;

    @JsonProperty("parentCategory")
    private AircraftCategoryDto parentCategory;

    @JsonProperty("subcategories")
    private List<AircraftCategoryDto> subcategories;
}
