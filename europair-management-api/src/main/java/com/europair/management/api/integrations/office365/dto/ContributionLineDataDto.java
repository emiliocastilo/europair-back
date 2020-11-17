package com.europair.management.api.integrations.office365.dto;

import com.europair.management.api.enums.PurchaseSaleEnum;
import com.europair.management.api.enums.ServiceTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContributionLineDataDto {

    private String routeLabel;

    private LocalDate routeStartDate;

    private LocalDate routeEndDate;

    private String comments;

    private BigDecimal price;

    private Integer quantity;

    private Boolean includedVAT;

    private PurchaseSaleEnum lineContributionRouteType;

    private ServiceTypeEnum type;
}
