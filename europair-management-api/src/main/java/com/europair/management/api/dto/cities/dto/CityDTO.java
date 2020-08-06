package com.europair.management.api.dto.cities.dto;

import com.europair.management.api.dto.audit.dto.AuditModificationBaseDTO;
import com.europair.management.api.dto.countries.dto.CountryDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDTO extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private CountryDTO country;

}
