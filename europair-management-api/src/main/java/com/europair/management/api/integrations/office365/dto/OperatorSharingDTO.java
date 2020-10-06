package com.europair.management.api.integrations.office365.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperatorSharingDTO {

    @JsonProperty("iataCode")
    private String iataCode;

    @JsonProperty("icaoCode")
    private String icaoCode;

    @JsonProperty("name")
    private String name;
}
