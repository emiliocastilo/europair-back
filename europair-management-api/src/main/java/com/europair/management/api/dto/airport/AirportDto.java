package com.europair.management.api.dto.airport;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.cities.CityDTO;
import com.europair.management.api.dto.common.MeasureDto;
import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.dto.countries.CountryDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("iataCode")
    @Size(max = TextField.IATA_CODE)
    @NotEmpty
    private String iataCode;

    @JsonProperty("icaoCode")
    @Size(max = TextField.ICAO_CODE)
    @NotEmpty
    private String icaoCode;

    @JsonProperty("name")
    @Size(max = TextField.TEXT_255)
    @NotEmpty
    private String name;

    @JsonProperty("country")
    private CountryDTO country;

    @JsonProperty("city")
    private CityDTO city;

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("elevation")
    private MeasureDto elevation;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("customs")
    private String customs;

    @JsonProperty("specialConditions")
    private Boolean specialConditions = false;

    @JsonProperty("flightRules")
    private String flightRules;

}
