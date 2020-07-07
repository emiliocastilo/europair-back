package com.europair.management.rest.model.screens.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("screen")
public class ScreenDTO {

  @JsonProperty("id")
  private final Long id;

  @JsonProperty("name")
  private final String name;

  @JsonProperty("description")
  private final String description;

}
