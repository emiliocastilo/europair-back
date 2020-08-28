package com.europair.management.rest.model.regionscountries.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class RegionCountryPK implements Serializable {

  private Long regionId;

  private Long countryId;

}
