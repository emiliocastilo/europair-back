package com.europair.management.rest.model.regionscountries.entity;

import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.regions.entity.Region;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "regions_countries")
@Data
public class RegionCountry implements Serializable {

  @EmbeddedId
  private RegionCountryPK id;

  @ManyToOne
  @MapsId("regionId")
  @JoinColumn(name = "region_id")
  private Region region;

  @ManyToOne
  @MapsId("countryId")
  @JoinColumn(name = "country_id")
  private Country country;

}
