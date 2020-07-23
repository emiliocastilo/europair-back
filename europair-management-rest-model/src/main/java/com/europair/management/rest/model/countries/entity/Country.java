package com.europair.management.rest.model.countries.entity;

import com.europair.management.rest.model.screens.entity.Screen;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "countries")
@Data
public class Country implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String code;

  @Column(unique = true)
  private String name;

}
