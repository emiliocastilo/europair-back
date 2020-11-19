package com.europair.management.rest.model.contracts.entity;

import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.enums.ContractStatesEnum;
import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.PurchaseSaleEnum;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntityHardAudited;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.files.entity.Client;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.entity.Provider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "contracts")
@Audited
@Data
public class Contract extends SoftRemovableBaseEntityHardAudited implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = TextField.TEXT_20, nullable = false, unique = true)
    private String code;

    @Column
    private String description;

    @NotNull
    @Column(name = "contract_type", nullable = false)
    private PurchaseSaleEnum contractType;

    @NotNull
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @NotAudited
    private File file;

    @NotNull
    @Column(name = "contribution_id", nullable = false)
    private Long contributionId;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "contribution_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @NotAudited
    private Contribution contribution;

    @Column(name = "client_id")
    private Long clientId;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
    @NotAudited
    private Client client;

    @Column(name = "provider_id")
    private Long providerId;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @NotAudited
    private Provider provider;

    @NotNull
    @Column(name = "contract_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractStatesEnum contractState;

    // ToDo
/*
1 contrato - n lineas de contrato
lienas de contrato = lineas de cotización
 */
//    @NotAudited
//    @OneToMany(mappedBy = "contribution", orphanRemoval = true)
//    private Set<LineContributionRoute> lineContributionRoute;


    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "price", precision = 12, scale = 4)
    private BigDecimal price;

    @Column(name = "vat_percentage")
    private Double vatPercentage;

    @Column(name = "vat_amount", precision = 12, scale = 4)
    private BigDecimal vatAmount;

//    @Column(name = "included_vat")
//    private Boolean includedVAT;

//    @Column(name = "purchase_vat_msg")
//    private String purchaseVATMsg;

//    @Column(name = "sale_vat_msg")
//    private String saleVATMsg;

//    @Column(name = "percentage_applied_on_sale_tax")
//    private Double percentageAppliedOnSaleTax;

//    @Column(name = "percentage_applied_on_purchase_tax")
//    private Double percentageAppliedOnPurchaseTax;
}
