package com.europair.management.impl.service.operators;


import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOperatorService {

  Page<OperatorDTO> findAllPaginated(Pageable pageable);
  OperatorDTO findById(Long id);
  OperatorDTO saveOperator(OperatorDTO operatorDTO);
  OperatorDTO updateOperator(Long id, OperatorDTO operatorDTO);
  void deleteOperator(Long id);
  Page<OperatorDTO> searchOperator(String filter, Pageable pageable);

  Page<OperatorsAirportsDTO> findOperatorAirportsByOperatorPaginated(Long operatorId, Pageable pageable);

  OperatorsAirportsDTO saveOperatorsAirports(Long operatorId, OperatorsAirportsDTO operatorsAirportsDTO);

}
