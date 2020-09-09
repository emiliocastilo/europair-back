package com.europair.management.api.dto.contributionaircrafts;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.fleet.AircraftDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContributionAircraftDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("contribution")
    private ContributionDTO contribution;

    @JsonProperty("aircraft")
    private AircraftDto aircraft;
}
