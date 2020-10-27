package com.europair.management.api.dto.files;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileAdditionalDataDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fileId")
    private Long fileId;

    @EqualsAndHashCode.Exclude
    @JsonProperty("file")
    private FileDTO file;


    @JsonProperty("flightMotive")
    @Size(max = 2000)
    private String flightMotive;

    @JsonProperty("connections")
    @Size(max = 2000)
    private String connections;

    @JsonProperty("limitations")
    @Size(max = 2000)
    private String limitations;

    @JsonProperty("fixedVariableFuel")
    @Size(max = 2000)
    private String fixedVariableFuel;

    @JsonProperty("luggage")
    @Size(max = 2000)
    private String luggage;

    @JsonProperty("specialLuggage")
    @Size(max = 2000)
    private String specialLuggage;

    @JsonProperty("onBoardService")
    @Size(max = 2000)
    private String onBoardService;

    @JsonProperty("specialRequests")
    @Size(max = 2000)
    private String specialRequests;

    @JsonProperty("otherCharges")
    @Size(max = 2000)
    private String otherCharges;

    @JsonProperty("operationalInfo")
    @Size(max = 2000)
    private String operationalInfo;
}
