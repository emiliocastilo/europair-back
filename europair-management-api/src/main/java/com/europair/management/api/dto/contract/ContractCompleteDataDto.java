package com.europair.management.api.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractCompleteDataDto extends ContractDto {

    @JsonProperty("conditions")
    private Set<ContractConditionDto> conditions;

    @JsonProperty("cancelFees")
    private Set<ContractCancelFeeDto> cancelFees;

}
