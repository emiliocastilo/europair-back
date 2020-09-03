package com.europair.management.rest.model.flights.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "flights")
@Data
public class Flight extends AuditModificationBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "departure_time")
  private LocalDate departureTime;

  @Column
  private String origin;

  @Column
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

  @ManyToOne
  private Route route;

}
