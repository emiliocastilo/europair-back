package com.europair.management.rest.model.flights.entity;

import com.europair.management.rest.model.contributions.entity.Contribution;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "flight_taxes")
@Data
public class FlightTax implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "contribution_id")
    private Long contributionId;

    @ManyToOne
    @JoinColumn(name = "contribution_id", nullable = false, insertable = false, updatable = false)
    private Contribution contribution;

    @Column(name = "flight_id")
    private Long flightId;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false, insertable = false, updatable = false)
    private Flight flight;

    @Column(name = "sale_tax_percentage")
    private Double taxOnSale;

    @Column(name = "sale_tax_amount")
    private BigDecimal taxAmountOnSale;

    @Column(name = "purchase_tax_percentage")
    private Double taxOnPurchase;

    @Column(name = "purchase_tax_amount")
    private BigDecimal taxAmountOnPurchase;

}
