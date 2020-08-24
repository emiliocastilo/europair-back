package com.europair.management.rest.model.regionsairports.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class RegionAirportPK implements Serializable {

  private Long regionId;
  private Long airportId;

}
