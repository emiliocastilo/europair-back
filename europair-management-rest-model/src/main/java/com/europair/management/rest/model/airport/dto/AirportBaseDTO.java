package com.europair.management.rest.model.airport.dto;

import com.europair.management.rest.model.airport.entity.Runway;
import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import com.europair.management.rest.model.cities.dto.CityDTO;
import com.europair.management.rest.model.countries.dto.CountryDTO;
import com.europair.management.rest.model.countries.entity.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportBaseDTO extends AuditModificationBaseDTO {

    private Long id;

    private String iataCode;

    private String icaoCode;

    private String name;

    private CountryDTO country;

    private CityDTO city;

    private RunwayDTO mainRunway;
}
