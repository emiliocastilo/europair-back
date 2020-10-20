package com.europair.management.rest.model.cities.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.countries.entity.Country;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cities")
@Data
public class City extends AuditModificationBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String code;

  @Column
  private String name;

  @Column(name = "canary_islands")
  private Boolean canaryIslands = false;

  @ManyToOne
  private Country country;

}
