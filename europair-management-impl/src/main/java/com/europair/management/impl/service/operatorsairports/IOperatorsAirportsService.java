package com.europair.management.impl.service.operatorsairports;

import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOperatorsAirportsService {

  Page<OperatorsAirportsDTO> findOperatorsAirportsByOperatorPaginated(Long operatorId, Pageable pageable);

  OperatorsAirportsDTO findOperatorsAirportsById(Long operatorId, Long id);

  OperatorsAirportsDTO saveOperatorsAirports(Long operatorId, OperatorsAirportsDTO operatorsAirportsDTO);

  OperatorsAirportsDTO updateOperatorsAirports(Long operatorId, Long id, OperatorsAirportsDTO operatorsAirportsDTO);

  void deleteOperatorsAirports(Long operatorId, Long id);

}
