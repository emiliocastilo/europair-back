package com.europair.management.api.dto.operatorsairports;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.operators.OperatorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatorsAirportsDTO extends AuditModificationBaseDTO {

  private Long id;

  private OperatorDTO operator;

  private AirportDto airport;

  private String comments;

}
