package com.europair.management.rest.model.taxes.entity;

import com.europair.management.rest.model.airport.entity.Airport;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class representing the percentage of the route with Balearics Islands that is liable to VAT
 */
@Entity
@Table(name = "vat_balearics")
@Data
public class RouteBalearicsPctVat implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "origin_airport_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
  private Airport originAirport;

  @Column(name = "origin_airport_id")
  private Long originAirportId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "destination_airport_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
  private Airport destinationAirport;

  @Column(name = "destination_airport_id")
  private Long destinationAirportId;

  @Column
  private Double percentage;

}
