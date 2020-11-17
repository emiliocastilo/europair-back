package com.europair.management.rest.model.flights.entity;

import com.europair.management.api.enums.CommonStateEnum;
import com.europair.management.api.enums.UTCEnum;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntityHardAudited;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.routes.entity.Route;
import lombok.Data;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flights")
@Audited
@Data
public class Flight extends AuditModificationBaseEntityHardAudited implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "departure_time")
  private LocalDateTime departureTime;

  @Column(name = "arrival_time")
  private LocalDateTime arrivalTime;

  @Column(name = "time_zone")
  @Enumerated(EnumType.STRING)
  private UTCEnum timeZone;

  @NotNull
  @Column(name = "origin_id", nullable = false)
  private Long originId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "origin_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
  private Airport origin;

  @NotNull
  @Column(name = "destination_id", nullable = false)
  private Long destinationId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
  private Airport destination;

  @Column(name = "flight_order")
  private Integer order;

  @Column(name = "seats_F")
  private Integer seatsF;

  @Column(name = "seats_C")
  private Integer seatsC;

  @Column(name = "seats_Y")
  private Integer seatsY;

  @Column
  private Integer beds;

  @Column
  private Integer stretchers;

  @NotNull
  @Column(name = "route_id", nullable = false)
  private Long routeId;

  @NotAudited
  @ManyToOne
  @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
  private Route route;

  @Column(name = "positional_flight")
  private Boolean positionalFlight;

  @Column(name = "flight_number", length = TextField.TEXT_10)
  private String flightNumber;

  @Column
  @Enumerated(value = EnumType.STRING)
  private CommonStateEnum slot;

  @Column
  @Enumerated(value = EnumType.STRING)
  private CommonStateEnum parking;

  @NotAudited
  @OneToMany(mappedBy = "flight", orphanRemoval = true)
  private List<FlightTax> taxes;

  @NotAudited
  @OneToMany(mappedBy = "flight", orphanRemoval = true)
  private List<FlightService> services;

  @Column(name = "sent_planning")
  private Boolean sentPlanning = Boolean.FALSE;


  @Column(name = "real_departure_time")
  private LocalDateTime realDepartureTime;

  @Column(name = "real_arrival_time")
  private LocalDateTime realArrivalTime;

  @Column(name = "pax_ad")
  private Integer paxAD;

  @Column(name = "pax_chd")
  private Integer paxCHD;

  @Column(name = "pax_infants")
  private Integer paxInfants;
}
