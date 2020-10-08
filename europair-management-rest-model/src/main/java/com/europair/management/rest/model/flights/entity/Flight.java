package com.europair.management.rest.model.flights.entity;

import com.europair.management.api.enums.UTCEnum;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Audited
@Data
public class Flight extends AuditModificationBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "departure_time")
  private LocalDateTime departureTime;

  @Column(name = "time_zone")
  @Enumerated(EnumType.STRING)
  private UTCEnum timeZone;

  @NotNull
  @Column(nullable = false)
  private String origin;

  @NotNull
  @Column(nullable = false)
  private String destination;

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

}
