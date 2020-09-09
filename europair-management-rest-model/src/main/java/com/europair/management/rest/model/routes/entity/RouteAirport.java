package com.europair.management.rest.model.routes.entity;

import com.europair.management.rest.model.airport.entity.Airport;
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

@Entity
@Table(name = "route_airports")
@Data
public class RouteAirport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_id")
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @Column(name = "airport_id")
    private Long airportId;

    @ManyToOne
    @JoinColumn(name = "airport_id")
    private Airport airport;

    @Column(name = "airport_order", nullable = false)
    private Integer order;

}
