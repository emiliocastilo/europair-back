package com.europair.management.rest.model.flights.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.files.entity.Provider;
import com.europair.management.rest.model.services.entity.Service;
import com.europair.management.rest.model.users.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "flight_services")
@Data
public class FlightService extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "flight_id", nullable = false)
    private Long flightId;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false, insertable = false, updatable = false)
    private Flight flight;

    @NotNull
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false, insertable = false, updatable = false)
    private Service service;

    @Column
    private String description;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false, insertable = false, updatable = false)
    private Provider provider;

    @NotNull
    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice;

    @NotNull
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;

    @Column(name = "sale_tax_percentage")
    private Double taxOnSale;

    @Column(name = "purchase_tax_percentage")
    private Double taxOnPurchase;

    @Column(name = "commission_percentage")
    private Double commission;

    @Column
    private String comments;

    @NotNull
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false, insertable = false, updatable = false)
    private User seller;

    // ToDo: pendiente estados
    @Column(length = TextField.TEXT_20)
    private String status;
}
