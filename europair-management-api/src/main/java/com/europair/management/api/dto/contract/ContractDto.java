package com.europair.management.api.dto.contract;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.files.ClientDto;
import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.dto.files.ProviderDto;
import com.europair.management.api.enums.ContractStatesEnum;
import com.europair.management.api.enums.PurchaseSaleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("description")
    private String description;

    @JsonProperty("contractType")
    private PurchaseSaleEnum contractType;

    @JsonProperty("fileId")
    private Long fileId;

    @JsonProperty("file")
    @EqualsAndHashCode.Exclude
    private FileDTO file;

    @JsonProperty("clientId")
    private Long clientId;

    @JsonProperty("client")
    @EqualsAndHashCode.Exclude
    private ClientDto client;

    @JsonProperty("providerId")
    private Long providerId;

    @JsonProperty("provider")
    @EqualsAndHashCode.Exclude
    private ProviderDto provider;

    @JsonProperty("contractState")
    private ContractStatesEnum contractState;

}
