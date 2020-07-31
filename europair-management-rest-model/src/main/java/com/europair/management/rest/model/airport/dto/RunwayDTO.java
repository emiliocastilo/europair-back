package com.europair.management.rest.model.airport.dto;

import com.europair.management.rest.model.common.dto.PhysicalQuantityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunwayDTO {

    private Long id;

    private PhysicalQuantityDTO length;

    private PhysicalQuantityDTO width;

    private String comments;
}
