package com.europair.management.api.dto.contract;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractCancelFeeDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("contractId")
    private Long contractId;

    @JsonProperty("contract")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ContractDto contract;

    @JsonProperty("fromValue")
    private Double fromValue;

    @JsonProperty("fromUnit")
    private String fromUnit;

    @JsonProperty("feePercentage")
    private Double feePercentage;
}
