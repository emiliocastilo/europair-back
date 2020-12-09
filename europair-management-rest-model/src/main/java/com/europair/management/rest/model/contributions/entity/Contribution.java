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
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private File file;

    @Column(name = "route_id")
    private Long routeId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Route route;

    @Column(name = "contribution_state")
    @Enumerated(EnumType.STRING)
    private ContributionStatesEnum contributionState;

    @Column(name = "operator_id")
    private Long operatorId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotAudited
    @ManyToOne
    @JoinColumn(name = "operator_id", insertable = false, updatable = false)
    private Operator operator;

    @Column(name = "aircraft_id")
    private Long aircraftId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotAudited
    @ManyToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Aircraft aircraft;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Column(name = "quoted_time")
    private LocalDateTime quotedTime;

    // maximum load who must be the airborne to the destiny
    @Column(name = "cargo_airborne")
    private Long cargoAirborne;


    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "currency_on_sale")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currencyOnSale;

    @Column(name = "purchase_comments", length = 255)
    private String purchaseComments;

    @Column(name = "sales_comments", length = 255)
    private String salesComments;

    // TODO: eliminar tiene sentido almacenar el tipo de cambio entre monedas si es un dato volatil??
    @Column(name = "exchange_buy_type")
    @Enumerated(EnumType.STRING)
    private ExchangeBuyTypeEnum exchangeBuyType;

    @Column(name = "purchase_price", precision = 12, scale = 4)
    private BigDecimal purchasePrice;

    // TODO: está relacionado con la tasa de vuelo?? a comentar ver tabla flightTaxes y los servicios de vuelo en flightService
    // una cotizacion puede tener muchas tasas de vuelo tantas como vuelos
    // existen tantos vuelos como rotaciones al menos ya que una rotacion contiene 1 - N vuelos
    // ¿que porcentaje deberemos almacenoar, aqui? ¿la media?
    @Column(name = "purchase_commission_percent")
    private Double purchaseCommissionPercent;

    @Column(name = "included_vat")
    private Boolean includedVAT;

    @Column(name = "sales_price", precision = 12, scale = 4)
    private BigDecimal salesPrice;


    // TODO: lo mismo pasa (que en el purchaseCommisionPercent) con el %de venta
    @Column(name = "sales_commission_percent")
    private Double salesCommissionPercent;

    @Column(name = "sales_price_without_vat", precision = 12, scale = 4)
    private BigDecimal salesPricewithoutVAT;

    @NotAudited
    @OneToMany(mappedBy = "contribution", orphanRemoval = true)
    private Set<LineContributionRoute> lineContributionRoute;

    @Column(name = "seating_f")
    private Integer seatingF;

    @Column(name = "seating_c")
    private Integer seatingC;

    @Column(name = "seating_y")
    private Integer seatingY;

    @Column(name = "vat_amount_sale", precision = 12, scale = 4)
    private BigDecimal vatAmountOnSale;

    @Column(name = "vat_amount_purchase", precision = 12, scale = 4)
    private BigDecimal vatAmountOnPurchase;

    @Column(name = "purchase_vat_msg")
    private String purchaseVATMsg;

    @Column(name = "sale_vat_msg")
    private String saleVATMsg;

    @Column(name = "percentage_applied_on_sale_tax")
    private Double percentageAppliedOnSaleTax;

    @Column(name = "percentage_applied_on_purchase_tax")
    private Double percentageAppliedOnPurchaseTax;
}
