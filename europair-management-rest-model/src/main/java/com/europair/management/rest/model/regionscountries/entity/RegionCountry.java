package com.europair.management.rest.model.regionscountries.entity;

import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.regions.entity.Region;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
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
