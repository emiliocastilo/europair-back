package com.europair.management.api.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractConditionCopyDto {

    @NotNull
    @JsonProperty("contractId")
    private Long contractId;

    @NotEmpty
    @JsonProperty("conditions")
    private Set<Long> conditions;

}
