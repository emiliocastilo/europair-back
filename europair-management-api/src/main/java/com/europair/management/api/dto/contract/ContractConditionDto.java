package com.europair.management.api.dto.contract;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractConditionDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("contractId")
    private Long contractId;

    @JsonProperty("contract")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ContractDto contract;

    @JsonProperty("conditionOrder")
    private Integer conditionOrder;

    @JsonProperty("code")
    private String code;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    @Size(max = 1000)
    private String description;

}
