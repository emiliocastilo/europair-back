package com.europair.management.api.dto.routes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteExtendedDto extends RouteDto {

    @JsonProperty("seatsF")
    private Integer seatsF;

    @JsonProperty("seatsC")
    private Integer seatsC;

    @JsonProperty("seatsY")
    private Integer seatsY;

    @JsonProperty("rotations")
    private List<RouteExtendedDto> rotationsExtended;

}
