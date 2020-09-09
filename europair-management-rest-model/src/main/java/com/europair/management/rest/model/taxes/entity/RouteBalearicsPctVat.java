package com.europair.management.rest.model.taxes;

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

  @Column(name = "origin_airport")
  private String originAirport;

  @Column(name = "destination_airport")
  private String destinationAirport;

  @Column
  private Double percentage;

}
