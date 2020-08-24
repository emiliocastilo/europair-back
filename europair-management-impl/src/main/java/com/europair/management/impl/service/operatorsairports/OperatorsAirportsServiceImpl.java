package com.europair.management.impl.service.operatorsairports;

import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.operatorsAirports.IOperatorsAirportsMapper;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.operators.repository.IOperatorRepository;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import com.europair.management.rest.model.operatorsairports.repository.OperatorsAirportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OperatorsAirportsServiceImpl implements IOperatorsAirportsService {

  private final OperatorsAirportsRepository operatorsAirportsRepository;
  private final IOperatorRepository operatorRepository;

  @Override
  public Page<OperatorsAirportsDTO> findOperatorsAirportsByOperatorPaginated(Long operatorId, Pageable pageable) {

    Operator operator = operatorRepository.findById(operatorId)
      .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + operatorId));

    Page<OperatorsAirports> airportsList = operatorsAirportsRepository.findByOperatorId(operatorId, pageable);

    return airportsList.map(operatorsAirports -> IOperatorsAirportsMapper.INSTANCE.toDto(operatorsAirports));
  }

  @Override
  public OperatorsAirportsDTO findOperatorsAirportsById(Long operatorId, Long id) {

    Operator operator = operatorRepository.findById(operatorId)
      .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + operatorId));

    return IOperatorsAirportsMapper.INSTANCE.toDto(operatorsAirportsRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("OperatorsAirports not found on id: " + id)));
  }

  @Transactional(readOnly = false)
  @Override
  public OperatorsAirportsDTO saveOperatorsAirports(Long operatorId, OperatorsAirportsDTO operatorsAirportsDTO) {

    Operator operator = operatorRepository.findById(operatorId)
      .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + operatorId));

    OperatorsAirports operatorsAirports = IOperatorsAirportsMapper.INSTANCE.toEntity(operatorsAirportsDTO);
    operatorsAirports.setOperator(operator);
    operatorsAirports = operatorsAirportsRepository.save(operatorsAirports);
    return IOperatorsAirportsMapper.INSTANCE.toDto(operatorsAirports);

  }

  @Transactional(readOnly = false)
  @Override
  public OperatorsAirportsDTO updateOperatorsAirports(Long operatorId, Long id, OperatorsAirportsDTO operatorsAirportsDTO) {

    Operator operator = operatorRepository.findById(operatorId)
      .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + operatorId));

    OperatorsAirports operatorsAirports = operatorsAirportsRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("OperatorsAirports not found on id: " + id));

      IOperatorsAirportsMapper.INSTANCE.updateFromDto(operatorsAirportsDTO, operatorsAirports);
      operatorsAirports.setOperator(operator);
      operatorsAirports = operatorsAirportsRepository.save(operatorsAirports);
      return IOperatorsAirportsMapper.INSTANCE.toDto(operatorsAirports);

  }

  @Override
  public void deleteOperatorsAirports(Long operatorId, Long id) {
    Operator operatorBD = operatorRepository.findById(operatorId)
      .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + operatorId));

    OperatorsAirports operatorsAirports = operatorsAirportsRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("OperatorsAirports not found on id: " + id));

    operatorsAirports.setRemovedAt(new Date());
    operatorRepository.save(operatorBD);
  }
}
