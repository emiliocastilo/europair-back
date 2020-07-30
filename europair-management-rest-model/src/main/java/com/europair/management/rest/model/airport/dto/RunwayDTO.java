package com.europair.management.rest.model.airport.dto;

import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import com.europair.management.rest.model.cities.dto.CityDTO;
import com.europair.management.rest.model.common.PhysicalQuantity;
import com.europair.management.rest.model.common.dto.PhysicalQuantityDTO;
import com.europair.management.rest.model.countries.dto.CountryDTO;
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
