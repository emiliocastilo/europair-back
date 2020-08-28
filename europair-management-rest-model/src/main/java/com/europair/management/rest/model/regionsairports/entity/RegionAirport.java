package com.europair.management.rest.model.regionsairports.entity;

import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.regions.entity.Region;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "regions_airports")
@Data
public class RegionAirport implements Serializable {

  @EmbeddedId
  private RegionAirportPK id;

  @ManyToOne
  @MapsId("regionId")
  @JoinColumn(name = "region_id")
  private Region region;

  @ManyToOne
  @MapsId("airportId")
  @JoinColumn(name = "airport_id")
  private Airport airport;

}
