package com.europair.management.api.dto.flights;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.dto.files.ProviderDto;
import com.europair.management.api.enums.FileServiceEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlightServiceDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("flightId")
    private Long flightId;

//    @JsonProperty("flight")
//    private FlightDTO flight;

    @JsonProperty("serviceType")
    @NotNull
    private FileServiceEnum serviceType;

    @JsonProperty("description")
    @Size(max = TextField.TEXT_255)
    private String description;

    @JsonProperty("quantity")
    @NotNull
    private Integer quantity;

    @JsonProperty("providerId")
    @NotNull
    private Long providerId;

    @JsonProperty("provider")
    private ProviderDto provider;

    @JsonProperty("purchasePrice")
    @NotNull
    private BigDecimal purchasePrice;

    @JsonProperty("salePrice")
    @NotNull
    private BigDecimal salePrice;

    @JsonProperty("taxOnSale")
    private Double taxOnSale;

    @JsonProperty("taxOnPurchase")
    private Double taxOnPurchase;

}
