package com.europair.management.rest.model.airport.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.common.Location;
import com.europair.management.rest.model.countries.entity.Country;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "airports")
@Data
public class Airport extends AuditModificationBaseEntity implements Serializable {

  public static enum Customs {YES, NO, ON_REQUEST};

  public static enum FlightRules {IFR, VFR};

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String iataCode;

  @Column(unique = true)
  private String icaoCode;

  private String name;

  @ManyToOne
  @JoinColumn(name="country_id")
  private Country country;

  @ManyToOne
  @JoinColumn(name="city_id")
  private City city;

  @Column(name = "time_zone")
  private String timeZone;

  private double elevation;

  @Embedded
  private Location location;

  @Enumerated(EnumType.STRING)
  private Customs customs;

  private boolean specialConditions = false;

  @Enumerated(EnumType.STRING)
  private FlightRules flightRules;
}
