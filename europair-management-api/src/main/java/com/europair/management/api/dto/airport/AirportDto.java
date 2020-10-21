package com.europair.management.api.dto.airport;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.cities.CityDTO;
import com.europair.management.api.dto.common.MeasureDto;
import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.dto.countries.CountryDTO;
import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.api.enums.UTCEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private UTCEnum timeZone;

    @JsonProperty("elevation")
    private MeasureDto elevation;

    @JsonProperty("latitude")
    private BigDecimal latitude;

    @JsonProperty("longitude")
    private BigDecimal longitude;

    @JsonProperty("customs")
    private String customs;

    @JsonProperty("specialConditions")
    private Boolean specialConditions = false;

    @JsonProperty("flightRules")
    private String flightRules;

    @JsonProperty("canary_islands")
    private Boolean canaryIslands = false;

    @JsonProperty("balearics")
    private Boolean balearics = false;

    @JsonProperty("terminals")
    private List<TerminalDto> terminals;

    @JsonProperty("runways")
    private List<RunwayDto> runways;

    @JsonProperty("observations")
    private List<AirportObservationDto> observations;

    @JsonProperty("operators")
    private List<OperatorsAirportsDTO> operators;

    @JsonProperty("regions")
    private List<RegionDTO> regions;

}
