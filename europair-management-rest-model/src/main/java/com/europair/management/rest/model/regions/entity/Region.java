package com.europair.management.rest.model.regions.entity;

import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.countries.entity.Country;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "regions")
@Data
public class Region extends AuditModificationBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String code;

  @Column(unique = true, length = TextField.TEXT_255)
  private String name;

  @ManyToMany
  @JoinTable(name = "regions_countries",
    joinColumns = @JoinColumn(name = "region_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
  private Set<Country> countries;

  @ManyToMany
  @JoinTable(name = "regions_airports",
    joinColumns = @JoinColumn(name = "region_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "airport_id", referencedColumnName = "id"))
  private Set<Airport> airports;


}
