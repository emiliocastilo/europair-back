package com.europair.management.api.dto.contract;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.UTCEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractConfigurationDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("contractId")
    private Long contractId;

    @JsonProperty("contract")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ContractDto contract;

    @JsonProperty("language")
    private String language;

    @JsonProperty("timezone")
    private UTCEnum timezone;

    @JsonProperty("paymentConditionsId")
    private Long paymentConditionsId;

    @JsonProperty("paymentConditions")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ContractPaymentConditionDto paymentConditions;

    @JsonProperty("paymentConditionsObservation")
    private String paymentConditionsObservation;

    @JsonProperty("currency")
    private CurrencyEnum currency;

    @JsonProperty("deposit")
    private BigDecimal deposit;

    @JsonProperty("depositExpirationDate")
    private LocalDate depositExpirationDate;

}
