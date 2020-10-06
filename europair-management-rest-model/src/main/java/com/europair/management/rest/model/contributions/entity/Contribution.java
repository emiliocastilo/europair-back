package com.europair.management.rest.model.contributions.entity;

import com.europair.management.api.enums.ContributionStates;
import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.ExchangeBuyTypeEnum;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.routes.entity.Route;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contributions")
@Data
public class Contribution extends SoftRemovableBaseEntity implements Serializable{

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
    private ContributionStates contributionState;

    @Column(name = "operator_id")
    private Long operatorId;

    @ManyToOne
    @JoinColumn(name = "operator_id", insertable = false, updatable = false)
    private Operator operator;

    @Column(name = "aircraft_id")
    private Long aircraftId;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Aircraft aircraft;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Column(name = "quoted_time")
    private LocalDateTime quotedTime;

    /* TODO: this hours must be in the route delete if needed

    // hour that confirms the airport to flight
    @Column(name = "confirmed_time")
    private LocalDateTime confirmedTime;

    private LocalDateTime departureRealTime;

    private LocalDateTime arriveRealTime;
    */


    // maximum load who must be the airborne to the destiny
    @Column(name = "cargo_airborne")
    private Long cargoAirborne;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

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

    @Column(name = "block_hour", precision = 12, scale = 4)
    private BigDecimal blockHour;

    @Column(name = "price_per_seat", precision = 12, scale = 4)
    private BigDecimal pricePerSeat;

    @Column(name = "price_per_pax", precision = 12, scale = 4)
    private BigDecimal pricePerPax;


    /**
     * Duda: no entiendo:
     *  + importe de comision
     *  + precioNetoCompra
     *  + precioNetoVenta
     */
    // importe de comision (
    // precioNetoCompra
    // precioNetoVenta


    // ¿el usuario puede consultar el historico de comunicaciones de cotizacion ?

    /**
     * PENDIENTE: A la hora de añadir precios debe poder indicarse otro usuario de Europair ya que la creación o modificación de importes influye en el resparto de comisiones
     * PENDIENTE: Otros tipos de importes en el expediente (en compra y venta siempre): servicios adicionales: tasas, combustible, catering. Etc.
     */

}
