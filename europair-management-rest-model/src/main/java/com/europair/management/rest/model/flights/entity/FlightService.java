package com.europair.management.rest.model.flights.entity;

import com.europair.management.api.enums.FileServiceEnum;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.files.entity.Provider;
import lombok.Data;

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
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "flight_services")
@Data
public class FlightService extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_id")
    private Long flightId;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false, insertable = false, updatable = false)
    private Flight flight;

    @Column(name = "service_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FileServiceEnum serviceType; // ToDo: será una tabla o enum??

    @Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "provider_id")
    private Long providerId;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false, insertable = false, updatable = false)
    private Provider provider;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "sale_tax_percentage", nullable = false)
    private Double taxOnSale;

    @Column(name = "purchase_tax_percentage", nullable = false)
    private Double taxOnPurchase;
}
