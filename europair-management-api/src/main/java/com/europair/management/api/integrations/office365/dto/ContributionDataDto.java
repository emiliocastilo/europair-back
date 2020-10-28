package com.europair.management.api.integrations.office365.dto;

import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.ExchangeBuyTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ContributionDataDto implements Serializable {

    private String aircraftType;
    private String aircraftBase;

    private CurrencyEnum currencyOnPurchase;
    private ExchangeBuyTypeEnum exchangeTypeOnPurchase;

    private BigDecimal purchasePrice;
    private Double purchaseCommissionPercentage;
    private BigDecimal purchaseCommissionAmount;
    private BigDecimal purchaseNetPrice;

    private boolean includedVAT;

    private CurrencyEnum currencyOnSale;
    private ExchangeBuyTypeEnum exchangeTypeOnSale;

    private BigDecimal purchasePriceOnSaleCurrency;

    private BigDecimal salePrice;
    private Double saleCommissionPercentage;
    private BigDecimal saleCommissionAmount;
    private BigDecimal saleNetPrice;

    private Double marginPercentage;
    private BigDecimal marginAmount;

    private String seller;

    private List<ContributionLineDataDto> lines;

    private Integer seatingF;
    private Integer seatingC;
    private Integer seatingY;

}
