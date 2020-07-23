package com.europair.management.rest.model.cities.entity;

import com.europair.management.rest.model.countries.entity.Country;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cities")
@Data
public class City implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String code;

  private String name;

  @ManyToOne
  private Country country;

}