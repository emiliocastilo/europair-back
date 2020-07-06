package com.europair.management.rest.model.screens.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenDTO {

  private Long id;
  private String name;
  private String description;

}
