package com.europair.management.rest.model.contributions.entity;

import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.ExchangeBuyTypeEnum;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntityHardAudited;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.routes.entity.Route;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "contributions")
@Audited
@Data
public class Contribution extends SoftRemovableBaseEntityHardAudited implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_id")
    private Long fileId;

    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private File file;

    @Column(name = "route_id")
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Route route;

    @Column(name = "contribution_state")
    @Enumerated(EnumType.STRING)
    private ContributionStatesEnum contributionState;

    @Column(name = "operator_id")
    private Long operatorId;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "operator_id", insertable = false, updatable = false)
    private Operator operator;

    @Column(name = "aircraft_id")
    private Long aircraftId;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Aircraft aircraft;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Column(name = "quoted_time")
    private LocalDateTime quotedTime;

    // maximum load who must be the airborne to the destiny
    // TODO: tiene sentido si agrupamos varias aeronaves ??
    @Column(name = "cargo_airborne")
    private Long cargoAirborne;


    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "currency_on_sale")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currencyOnSale;

    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "exchange_buy_type")
    @Enumerated(EnumType.STRING)
    private ExchangeBuyTypeEnum exchangeBuyType;

    @Column(name = "purchase_price", precision = 12, scale = 4)
    private BigDecimal purchasePrice;

    @Column(name = "purchase_commission_percent")
    private Integer purchaseCommissionPercent;

    @Column(name = "included_vat")
    private Boolean includedVAT;

    @Column(name = "sales_price", precision = 12, scale = 4)
    private BigDecimal salesPrice;

    @Column(name = "sales_commission_percent")
    private Integer salesCommissionPercent;

    @Column(name = "sales_price_without_vat", precision = 12, scale = 4)
    private BigDecimal salesPricewithoutVAT;

    @NotAudited
    @OneToMany(mappedBy = "contribution", orphanRemoval = true)
    private Set<LineContributionRoute> lineContributionRoute;

}
